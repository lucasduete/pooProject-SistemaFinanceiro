package com.github.SistemaFinanceiro.resources;

import com.github.SistemaFinanceiro.dao.MovimentacaoArquivoDao;
import com.github.SistemaFinanceiro.dao.MovimentacaoBancoDao;
import com.github.SistemaFinanceiro.exceptions.NullDirectoryException;
import com.github.SistemaFinanceiro.interfaces.MovimentacaoDaoInterface;
import com.github.SistemaFinanceiro.interfaces.SGBDErrosInterface;
import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Lucas Duete
 * @version 1.0
 * @since 8.0
 */
public class MovimentacaoBackupManagement implements SGBDErrosInterface, MovimentacaoDaoInterface {
    
    public static boolean OP_INSERT = false;
    public static boolean OP_UPDATE = false;
    public static boolean OP_REMOVE = false;
    
    private static final Stack<MovimentacaoFinanceira> movimentacoesSalvar = new Stack<>();
    private static final Stack<MovimentacaoFinanceira> movimentacoesRemover = new Stack<>();
    private static final Stack<MovimentacaoFinanceira> movimentacoesAtualizar = new Stack<>();
    
    private final CountDownLatch delay = new CountDownLatch (1);
    
    private final MovimentacaoArquivoDao movimentacaoDao;
    
    public MovimentacaoBackupManagement(int idUsuario) throws NullDirectoryException, IOException {
        movimentacaoDao = new MovimentacaoArquivoDao(idUsuario);
    }

    @Override
    public boolean salvar(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        OP_INSERT = true;
        movimentacoesSalvar.push(movimentacao);
        atualizarBD(1);
        return movimentacaoDao.salvar(movimentacao);
    }
    
    @Override
    public boolean remover(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        OP_REMOVE = true;
        movimentacoesRemover.push(movimentacao);
        atualizarBD(2);
        return movimentacaoDao.remover(movimentacao);    
    }

    @Override
    public boolean atualizar(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        OP_UPDATE = true;
        movimentacoesAtualizar.push(movimentacao);
        atualizarBD(3);
        return movimentacaoDao.atualizar(movimentacao);
    }
    
    @Override
    public List<MovimentacaoFinanceira> listarByUsuario(int idUsuario) throws ClassNotFoundException, IOException, SQLException {
        return movimentacaoDao.listarByUsuario(idUsuario);
    }

    @Override
    public List<MovimentacaoFinanceira> listar() throws ClassNotFoundException, IOException, SQLException {
        return movimentacaoDao.listar();
    }

    @Override
    public MovimentacaoFinanceira getById(int id) throws IOException, ClassNotFoundException, SQLException {
        return movimentacaoDao.getById(id);
    }

    private void atualizarBD(int mode) {
        if (    (OP_INSERT == false && mode == 1) ||
                (OP_REMOVE == false && mode == 2) ||
                (OP_UPDATE == false && mode == 3))
            
            return;
        
        Thread atualizar = new Thread() {
            @Override
            public void run() {
                try {
                
                    MovimentacaoBancoDao daoBanco = new MovimentacaoBancoDao();
                    
                    switch (mode) {
                        case 1: 
                            while(!movimentacoesSalvar.empty())
                                daoBanco.salvar(movimentacoesSalvar.pop());
                            OP_INSERT = false; 
                            break;
                        case 2 :
                            while(!movimentacoesRemover.empty())
                                daoBanco.remover(movimentacoesRemover.pop());
                            OP_REMOVE = false;
                            break;
                        case 3:
                            while(!movimentacoesAtualizar.empty())
                                daoBanco.atualizar(movimentacoesAtualizar.pop());
                            OP_UPDATE = false;
                            break;
                    }

                } catch (ClassNotFoundException | SQLException | IOException ex) {
                    try {
                        //Espera por 10 min
                        delay.await(10, TimeUnit.MINUTES);

                        run();
                    } catch (InterruptedException ex1) {
                        ex.printStackTrace();
                        Logger.getLogger(UsuarioBackupManagement.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        };
        
        new Thread(atualizar).start();
    }

}


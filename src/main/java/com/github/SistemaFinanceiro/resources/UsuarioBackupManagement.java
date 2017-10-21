package com.github.SistemaFinanceiro.resources;

import com.github.SistemaFinanceiro.dao.UsuarioArquivoDao;
import com.github.SistemaFinanceiro.dao.UsuarioBancoDao;
import com.github.SistemaFinanceiro.exceptions.UniqueException;
import com.github.SistemaFinanceiro.interfaces.SGBDErrosInterface;
import com.github.SistemaFinanceiro.interfaces.UserDaoInterface;
import com.github.SistemaFinanceiro.model.Usuario;
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
 * @version 1.2
 * @since 8.0
 */
public class UsuarioBackupManagement implements SGBDErrosInterface, UserDaoInterface {
    
    public static boolean OP_INSERT = false;
    public static boolean OP_UPDATE = false;
    public static boolean OP_REMOVE = false;
    
    private static final Stack<Usuario> usuariosSalvar = new Stack<>();
    private static final Stack<Usuario> usuariosRemover = new Stack<>();
    private static final Stack<Usuario> usuariosAtualizar = new Stack<>();
    
    private final CountDownLatch delay = new CountDownLatch (1);
    
    private final UsuarioArquivoDao usuarioDao;
    
    public UsuarioBackupManagement() throws IOException {
        usuarioDao = new UsuarioArquivoDao();
    }
    
    @Override
    public boolean salvar(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, UniqueException {
        OP_INSERT = true;
        usuariosSalvar.push(user);
        atualizarBD(1);
        return usuarioDao.salvar(user);
    }

    @Override
    public List<Usuario> listar() throws ClassNotFoundException, IOException, SQLException {
        return usuarioDao.listar();
    }

    @Override
    public boolean remover(Usuario user) throws ClassNotFoundException, IOException, SQLException {
        OP_REMOVE = true;
        usuariosRemover.push(user);
        atualizarBD(2);
        return usuarioDao.remover(user);
    }

    @Override
    public boolean atualizar(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, UniqueException {
        OP_UPDATE = true;
        usuariosAtualizar.push(user);
        atualizarBD(3);
        return usuarioDao.atualizar(user);
    }

    @Override
    public Usuario getById(int id) throws IOException, ClassNotFoundException, SQLException {
        return usuarioDao.getById(id);
    }

    @Override
    public int userLogin(String email, String password) throws IOException, ClassNotFoundException, SQLException {
        return usuarioDao.userLogin(email, password);
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
                
                    UsuarioBancoDao daoBanco = new UsuarioBancoDao();

                    
                    switch (mode) {
                        case 1: 
                            while(!usuariosSalvar.empty())
                                daoBanco.salvar(usuariosSalvar.pop());
                            OP_INSERT = false; 
                            break;
                        case 2 :
                            while(!usuariosRemover.empty())
                                daoBanco.remover(usuariosRemover.pop());
                            OP_REMOVE = false; 
                            break;
                        case 3:
                            while(!usuariosAtualizar.empty())
                                daoBanco.atualizar(usuariosAtualizar.pop());
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


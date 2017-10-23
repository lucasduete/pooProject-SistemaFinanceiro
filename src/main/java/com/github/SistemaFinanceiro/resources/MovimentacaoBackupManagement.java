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
 * Esta Classe Encapsula Toda as Funçoes de Gerencia do Banco de Dados e Backup Para
 * as Operaçoes de Movimentaçoes Financeiras Quando o Sistema Utiliza Apenas os 
 * Backups por Falhas no Banco de Dados,
 * @author Lucas Duete
 * @version 2.0
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
    
    /**
     * Construtor Padrao Para Classe MovimentacaoBackupManagement Onde e Instanciado um 
     * ArquivoDao para Movimentacao Financeira.
     * @param idUsuario Variavel Inteira que Contem o Id do Usuario que Esta Manipulando 
     * os Backups.
     * @throws IOException Disparada quando Ocorre Erro ao Acesso ao Arquivos.
     * @throws NullDirectoryException Disparada Quando o Diretorio de Backups e Excluido 
     * em Tempo de Execuçao da Aplicacao.
     */
    
    public MovimentacaoBackupManagement(int idUsuario) throws NullDirectoryException, IOException {
        movimentacaoDao = new MovimentacaoArquivoDao(idUsuario);
    }
    
    /**
     * Este Metodo Realiza a Operaçao de Salvar de uma Nova Movimentacao Tanto no Banco de 
     * Dados como no Backup em Arquivos Binarios Utilizando Threads Assincronas para Gerenciar a Operaçao no 
     * Banco de Dados Garantindo que Seja Efetuada Assim que Possivel mas Gerenciando o Sistema 
     * usando os Backups.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceira que Contem a Movimentacao que 
     * sera Salva.
     * @return True Se Foi Possivel Salvar a Movimentacao, False Se Nao foi Possivel Salvar a Movimentacao.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */

    @Override
    public boolean salvar(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
        
        movimentacoesSalvar.push(movimentacao);
        
        if(!OP_INSERT) {
            OP_INSERT = true;
            atualizarBD(1);
        }
        
        return movimentacaoDao.salvar(movimentacao);
    }
    
    /**
     * Este Metodo Realiza a Remocao de uma Movimentacao Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios Utilizando Threads Assincronas para Gerenciar a Operaçao no 
     * Banco de Dados Garantindo que Seja Efetuada Assim que Possivel mas Gerenciando o Sistema 
     * usando os Backups.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceira que contem a Movimentacao que sera Removida
     * do Banco de Dados e Do Backup.
     * @return True Se Foi Possivel Deletar a Movimentacao, False Se Nao foi Possivel Remover a Movimentacao.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    @Override
    public boolean remover(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
        
        movimentacoesRemover.push(movimentacao);
        
        if(!OP_REMOVE) {
            OP_REMOVE = true;
            atualizarBD(2);
        }
        
        return movimentacaoDao.remover(movimentacao);    
    }

    /**
     * Este Metodo Realiza a Operaçao de Atualizacao de uma Movimentacao Tanto no Banco de Dados 
     * como no Backup em Arquivos Binarios Utilizando Threads Assincronas para Gerenciar a Operaçao no 
     * Banco de Dados Garantindo que Seja Efetuada Assim que Possivel mas Gerenciando o Sistema 
     * usando os Backups.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceira que contem os novos dados 
     * de uma Movimentacao para ser Atualizada no Banco de Dados e no Backup.
     * @return True Se Foi Possivel Atualizar a Movimentacao, False Se Nao foi Possivel Atualizar a Movimentacao.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    @Override
    public boolean atualizar(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
        
        movimentacoesAtualizar.push(movimentacao);
        
        if(!OP_UPDATE) {
            OP_UPDATE = true;
            atualizarBD(3);
        }
        
        return movimentacaoDao.atualizar(movimentacao);
    }
    
    /**
     * Este Metodo Lista todas as Movimentacoes Cadastradas de um Usuario.
     * @param idUsuario Variavel Inteira que contem o Id do Usuario do qual sera procurado as 
     * movimentacoes.
     * @return ArrayList de Objetos do Tipo MovimentacaoFinanceira deste Usuario, Tal 
     * Lista retorna Vazia quando Nao Existem Movimentacoes Cadastradas para Tal Usuario.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    @Override
    public List<MovimentacaoFinanceira> listarByUsuario(int idUsuario) 
            throws ClassNotFoundException, IOException, SQLException {
        return movimentacaoDao.listarByUsuario(idUsuario);
    }
    
    /**
     * Este Metodo Realiza a Operaçao de Listagem de Todas as Movimentacoes Financeiras 
     * Salvas no Banco de Dados e nos Backups Utilizando Threads Assincronas para Gerenciar a Operaçao no 
     * Banco de Dados Garantindo que Seja Efetuada Assim que Possivel mas Gerenciando o Sistema 
     * usando os Backups.
     * @return ArrayList de Objetos do TipoMovimentacaoFinanceira, Retorna uma Lista Vazia Caso 
     * Nao Haja Nenhuma Movimentacao Salva.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */

    @Override
    public List<MovimentacaoFinanceira> listar() throws ClassNotFoundException, IOException, SQLException {
        return movimentacaoDao.listar();
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco de Dados Retornando Todas as 
     * Informaçoes sobre uma Movimentacao Financeira Salva no Banco de Dados.
     * @param idMovimentacao Variavel Inteira que contem o Id da Movimentacao Financeira cuja 
     * qual Deseja-se as Informaçoes.
     * @return Objeto do Tipo MovimentacaoFinanceira Preenchida com Todas as Informaçoes Referentes 
     * aquela Movimentacao Salva no Banco com tal Id.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */

    @Override
    public MovimentacaoFinanceira getById(int idMovimentacao) 
            throws IOException, ClassNotFoundException, SQLException {
        return movimentacaoDao.getById(idMovimentacao);
    }

    /**
     * Metodo Privado que Inicia uma Thread Que Sera Responsavel por Gerenciar a Atualizaçao do Banco de 
     * Dados Mantendo-o com os Dados das Movimentacoes Financeiras Atualizados Assim que Possivel.
     * @param mode Variavel Inteira que Define o Modo de Funcionamento: 
     * 1 Para Inserçao; 2 Para Remocao; 3 Para Atualizaçao.
     */
        
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


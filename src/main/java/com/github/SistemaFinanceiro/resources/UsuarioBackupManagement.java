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
 * Esta Classe Encapsula Toda as Funçoes de Gerencia do Banco de Dados e Backup Para
 * as Operaçoes de Usuarios Quando o Sistema Utiliza Apenas os Backups por Falhas no 
 * Banco de Dados,
 * @author Lucas Duete
 * @version 2.0
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
    
    /**
     * Construtor Padrao Para Classe UsuarioBackupManagement Onde e Instanciado um 
     * ArquivoDao para o Usuario.
     * @throws IOException Disparada quando Ocorre Erro ao Acesso ao Arquivos.
     */
    
    public UsuarioBackupManagement() throws IOException {
        usuarioDao = new UsuarioArquivoDao();
    }
    
    /**
     * Este Metodo Realiza a Operaçao de Salvar de um Novo Usuario Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios Utilizando Threads Assincronas para Gerenciar a Operaçao no 
     * Banco de Dados Garantindo que Seja Efetuada Assim que Possivel mas Gerenciando o Sistema 
     * usando os Backups.
     * @param user Objeto do Tipo Usuario que Contem os dados do Usuario que sera Salvo.
     * @return True Se Foi Possivel Salvar o Usuario, False Se Nao foi Possivel Salvar o Usuario.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     * @throws UniqueException Disparada Qaundo e Violada a Restriçao de Unicidade do Email de um 
     * Usuario.
     */
    
    @Override
    public boolean salvar(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, UniqueException {
        
        usuariosSalvar.push(user);
        
        if(!OP_INSERT) {
            OP_INSERT = true;
            atualizarBD(1);
        }
        
        return usuarioDao.salvar(user);
    }

    /**
     * Este Metodo Realiza a Operaçao de Listagem de Todas os Usuarios 
     * Salvos no Banco de Dados e nos Backups.
     * @return ArrayList de Objetos do Tipo Usuario, Retorna uma Lista Vazia Caso Nao Haja 
     * Nenhum Usuario Salvo.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    @Override
    public List<Usuario> listar() throws ClassNotFoundException, IOException, SQLException {
        return usuarioDao.listar();
    }
    
    /**
     * Este Metodo Realiza a Operacao de Remocao de um Usuario Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios Utilizando Threads Assincronas para Gerenciar a Operaçao no 
     * Banco de Dados Garantindo que Seja Efetuada Assim que Possivel mas Gerenciando o Sistema 
     * usando os Backups.
     * @param user Objeto do Tipo Usuario que contem o Usuario que sera Removido do Banco de 
     * Dados e Do Backup.
     * @return True Se Foi Possivel Deletar o Usuario, False Se Nao foi Possivel Remover o Usuario.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */

    @Override
    public boolean remover(Usuario user) throws ClassNotFoundException, IOException, SQLException {
        
        usuariosRemover.push(user);
        
        if(!OP_REMOVE) {
            OP_REMOVE = true;
            atualizarBD(2);
        }
        
        return usuarioDao.remover(user);
    }
    
    /**
     * Este Metodo Realiza a Operaçao de Atualizacao de um Usuario Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios Utilizando Threads Assincronas para Gerenciar a Operaçao no 
     * Banco de Dados Garantindo que Seja Efetuada Assim que Possivel mas Gerenciando o Sistema 
     * usando os Backups.
     * @param user Objeto do Tipo Usuario que contem os novos dados de um Usuario 
     * para ser Atualizado no Banco de Dados e no Backup.
     * @return True Se Foi Possivel Atualizar o Usuario, False Se Nao foi Possivel Atualizar o Usuario.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     * @throws UniqueException Disparada Qaundo e Violada a Restriçao de Unicidade do Email de um 
     * Usuario.
     */

    @Override
    public boolean atualizar(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, UniqueException {
        
        usuariosAtualizar.push(user);
        
        if(!OP_UPDATE) {
            OP_UPDATE = true;
            atualizarBD(3);   
        }
        
        return usuarioDao.atualizar(user);
    }
    
    /**
     * Este Metodo Realiza a Retorno do Dados Armazenados Sobre um Usuario Usando o Banco de Dados 
     * e/ou no Backup em Arquivos Binarios.
     * @param id Variavel Inteira que Contem o ID do Usuario.
     * @return Objeto do Tipo Usuario Contendo as Informaçoes Armazenadas Sobre o Usuario com 
     * tal Id.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */

    @Override
    public Usuario getById(int id) throws IOException, ClassNotFoundException, SQLException {
        return usuarioDao.getById(id);
    }
    
    /**
     * Este Metodo Realiza a Operacao de Atutenticaçao de um Usuario Usando os Dados Contidos 
     * no Banco de Dados e/ou no Backup em Arquivos Binarios.
     * @param email String que Contem o Email do Usuario.
     * @param password String que Contem a Senha do Usuario.
     * @return Variavel Inteira Contendo o ID do Usuario Logado, Caso Seja -1 Entao as Credenciais Sao
     * Invalidas.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */

    @Override
    public int userLogin(String email, String password) throws IOException, ClassNotFoundException, SQLException {
        return usuarioDao.userLogin(email, password);
    }
    
    /**
     * Metodo Privado que Inicia uma Thread Que Sera Responsavel por Gerenciar a Atualizaçao do Banco de 
     * Dados Mantendo-o com os Dados dos Usuarios Atualizados Assim que Possivel.
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


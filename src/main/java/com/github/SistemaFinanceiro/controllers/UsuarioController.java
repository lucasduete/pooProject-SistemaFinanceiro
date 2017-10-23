package com.github.SistemaFinanceiro.controllers;

import com.github.SistemaFinanceiro.dao.UsuarioArquivoDao;
import com.github.SistemaFinanceiro.dao.UsuarioBancoDao;
import com.github.SistemaFinanceiro.exceptions.AtualizacaoUsuarioInvalidaException;
import com.github.SistemaFinanceiro.exceptions.FailDaoException;
import com.github.SistemaFinanceiro.exceptions.UniqueException;
import com.github.SistemaFinanceiro.interfaces.SGBDErrosInterface;
import com.github.SistemaFinanceiro.interfaces.UserDaoInterface;
import com.github.SistemaFinanceiro.model.Usuario;
import com.github.SistemaFinanceiro.resources.UsuarioBackupManagement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Esta Classe Encapsula todos os Metodos de Controle de Usurios 
 * como o Metodo para Salvar e Autenticar.
 * @author Lucas Duete
 * @version 2.5
 * @since 8.0
 */

public class UsuarioController implements UserDaoInterface, SGBDErrosInterface {
    
    public UserDaoInterface usuarioDao;
    private static boolean ERROR_BD = false;
    
    /**
     * Construtor Padrao da Classe UsuarioController Onde e Instanciada a um Objeto do 
     * Tipo UsuarioDao para Realizar as Operacoes em Banco e, em Caso de Erro, 
     * Gerencia-se o BackupManagement.
     */
    
    public UsuarioController () {
        try {
            usuarioDao = new UsuarioBancoDao();
            
        } catch (ClassNotFoundException | SQLException ex) {
            
            try {            
                instanciaError(ex);
                
            } catch (FailDaoException ex1) {
                JOptionPane.showMessageDialog(null, "Falha Total de Acesso aos Dados de Sistema",
                        "SEVERAL ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    /**
     * Este Metodo Realiza a Operaçao de Salvar de um Novo Usuario Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios.
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
        
        if(!ERROR_BD)
            if(usuarioDao.salvar(user))
                return new UsuarioArquivoDao().salvar(user);
        
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
     * Este Metodo Realiza a Operaçao de Atualizacao de um Usuario Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios.
     * @param user Objeto do Tipo Usuario que contem os novos dados de um Usuario 
     * para ser Atualizado no Banco de Dados e no Backup.
     * @return True Se Foi Possivel Atualizar o Usuario, False Se Nao foi Possivel Atualizar o Usuario.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     * @throws UniqueException Disparada Qaundo e Violada a Restriçao de Unicidade do Email de um 
     * Usuario.
     * @deprecated Pois Neste Metodo Nao Sao Checados Restriçoes Relacionadas as Senhas.
     */

    @Override
    @Deprecated
    public boolean atualizar(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, UniqueException {        
        
        if(!ERROR_BD)
            if(usuarioDao.atualizar(user))
                return new UsuarioArquivoDao().atualizar(user);
        
        return usuarioDao.atualizar(user);
    }
    
    /**
     * Este Metodo Realiza a Operacao de Remocao de um Usuario Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios.
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
        if(!ERROR_BD)
            if(usuarioDao.remover(user))
                return new UsuarioArquivoDao().remover(user);
        
        return usuarioDao.remover(user);
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
     * Este Metodo Realiza a Retorno do Dados Armazenados Sobre um Usuario Usando o Banco de Dados 
     * e/ou no Backup em Arquivos Binarios.
     * @param Id Variavel Inteira que Contem o ID do Usuario.
     * @return Objeto do Tipo Usuario Contendo as Informaçoes Armazenadas Sobre o Usuario com 
     * tal Id.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    @Override
    public Usuario getById(int Id) throws ClassNotFoundException, IOException, SQLException {
        return usuarioDao.getById(Id);
    }
    
    /**
     * Este Metodo Realiza a Operaçao de Atualizacao de um Usuario, Checando a Validade da Senha Mas 
     * Sem Modifica-la, Tanto no Banco de Dados como no Backup em Arquivos Binarios.
     * @param user Objeto do Tipo Usuario que contem os novos dados de um Usuario 
     * para ser Atualizado no Banco de Dados e no Backup.
     * @return True Se Foi Possivel Atualizar o Usuario, False Se Nao foi Possivel Atualizar o Usuario.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     * @throws UniqueException Disparada Qaundo e Violada a Restriçao de Unicidade do Email de um 
     * Usuario.
     * @throws AtualizacaoUsuarioInvalidaException Disparada Quando a Senha Do Usuario nao e Igual a
     * Antiga Senha Cadastrada no sistema.
     */
    
    public boolean atualizarSafe(Usuario user) 
            throws  ClassNotFoundException, IOException, SQLException, AtualizacaoUsuarioInvalidaException, 
                    UniqueException {
        
        if (! (validarSenha(user.getId(), user.getPassword())))
            throw new AtualizacaoUsuarioInvalidaException("Senha Antiga Invalida");
        
        if(!ERROR_BD)
            if(usuarioDao.atualizar(user))
                return new UsuarioArquivoDao().atualizar(user);
        
        return usuarioDao.atualizar(user);
    }
    
    /**
     * Este Metodo Realiza a Operaçao de Atualizacao de um Usuario, Checando a Validade da Senha E 
     * Modificando-a por uma Nova, Tanto no Banco de Dados como no Backup em Arquivos Binarios.
     * @param user Objeto do Tipo Usuario que contem os novos dados de um Usuario 
     * para ser Atualizado no Banco de Dados e no Backup.
     * @param novaSenha String que Contem a Nova Senha para Este Usuario.
     * @param antigaSenha String que Contem a Senha Antiga deste Usuario.
     * @return True Se Foi Possivel Atualizar o Usuario, False Se Nao foi Possivel Atualizar o Usuario.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     * @throws UniqueException Disparada Qaundo e Violada a Restriçao de Unicidade do Email de um 
     * Usuario.
     * @throws AtualizacaoUsuarioInvalidaException Disparada Quando a Senha Do Usuario nao e Igual a
     * Antiga Senha Cadastrada no sistema.
     */
    
    public boolean atualizarSafe(Usuario user, String novaSenha, String antigaSenha) 
            throws  ClassNotFoundException, IOException, SQLException, AtualizacaoUsuarioInvalidaException,
                    UniqueException {
        
        if (! (validarSenha(user.getId(), antigaSenha)))
            throw new AtualizacaoUsuarioInvalidaException("Senha Antiga Invalida");
        
        user.setPassword(novaSenha);
        if(!ERROR_BD)
            if(usuarioDao.atualizar(user))
                return new UsuarioArquivoDao().atualizar(user);
        
        return usuarioDao.atualizar(user);
    }
    
    /**
     * Metodo que Valida a Senha de Um Usuario Checando se tal Senha Coincide Com a Senha Armazenada 
     * Previamente no Sistema.
     * @param Id Variavel Inteira Que Contem o Id do Usuario que Sera Validado.
     * @param password Variavel String que Contem a Senha do Usuario a Ser Validada;
     * @return True se a Senha For Validada Com Sucesso e False se a Senha Nao Coincidir.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    private boolean validarSenha(int Id, String password) throws ClassNotFoundException, IOException, SQLException {
        Usuario user = usuarioDao.getById(Id);
        
        return (user.getPassword().equals(password));
    }
    
    /**
     * Metodo Privado Que Instancia um BackupManagement Com Base na Analise de uma Exception.
     * @param ex Objeto do Tipo Exception que Contem a Exception Disparada que Sera Analizada.
     * @throws FailDaoException Disparada Quando Nao For Possivel Acessar Nem o Banco de Dados 
     * Nem os Arquivos de Backup.
     */
    
    private void instanciaError(Exception ex) throws FailDaoException {
        
        if (!ex.getMessage().contains(ERROR_GERAL))
                return;
        
        try {
            
            if (ERROR_BD == false) {
                JOptionPane.showMessageDialog(null, "Falha na Conexão com o Banco, Usando Backups",
                        "TEMPORAL ERROR", JOptionPane.INFORMATION_MESSAGE);
                ERROR_BD = true;
            }
            
            
            usuarioDao = new UsuarioBackupManagement();
            
            
        } catch (IOException ex1) {
            throw new FailDaoException();
        }
    }
    
}
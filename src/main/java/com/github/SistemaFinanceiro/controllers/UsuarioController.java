package com.github.SistemaFinanceiro.controllers;

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
 * @version 2.1
 * @since 8.0
 */

public class UsuarioController implements UserDaoInterface, SGBDErrosInterface {
    
    public UserDaoInterface usuarioDao;
    private static boolean ERROR_BD = false;
    
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

    @Override
    public boolean salvar(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, UniqueException {
        return usuarioDao.salvar(user);
    }

    @Override
    public List<Usuario> listar() throws ClassNotFoundException, IOException, SQLException {
        return usuarioDao.listar();
    }

    @Override
    public boolean remover(Usuario user) throws ClassNotFoundException, IOException, SQLException {
        return usuarioDao.remover(user);
    }

    @Override
    @Deprecated
    public boolean atualizar(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, UniqueException {        
        return usuarioDao.atualizar(user);
    }

    @Override
    public int userLogin(String email, String password) throws IOException, ClassNotFoundException, SQLException {
        return usuarioDao.userLogin(email, password);
    }
    
    @Override
    public Usuario getById(int Id) throws ClassNotFoundException, IOException, SQLException {
        return usuarioDao.getById(Id);
    }
    
    public boolean atualizarSafe(Usuario user) 
            throws  ClassNotFoundException, IOException, SQLException, AtualizacaoUsuarioInvalidaException, 
                    UniqueException {
        
        if (! (validarSenha(user.getId(), user.getPassword())))
            throw new AtualizacaoUsuarioInvalidaException("Senha Antiga Invalida");
        
        return usuarioDao.atualizar(user);
    }
    
    public boolean atualizarSafe(Usuario user, String novaSenha, String antigaSenha) 
            throws  ClassNotFoundException, IOException, SQLException, AtualizacaoUsuarioInvalidaException,
                    UniqueException {
        
        if (! (validarSenha(user.getId(), antigaSenha)))
            throw new AtualizacaoUsuarioInvalidaException("Senha Antiga Invalida");
        
        user.setPassword(novaSenha);
        return usuarioDao.atualizar(user);
    }
    
    private boolean validarSenha(int Id, String password) throws ClassNotFoundException, IOException, SQLException {
        Usuario user = usuarioDao.getById(Id);
        
        return (user.getPassword().equals(password));
    }
    
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
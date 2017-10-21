package com.github.SistemaFinanceiro.controllers;

import com.github.SistemaFinanceiro.dao.UsuarioBancoDao;
import com.github.SistemaFinanceiro.exceptions.AtualizacaoUsuarioInvalidaException;
import com.github.SistemaFinanceiro.interfaces.UserDaoInterface;
import com.github.SistemaFinanceiro.model.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Esta Classe Encapsula todos os Metodos de Controle de Usurios 
 * como o Metodo para Salvar e Autenticar.
 * @author Lucas Duete
 * @version 2.0
 * @since 8.0
 */

public class UsuarioController implements UserDaoInterface {
    
    public UserDaoInterface usuarioDao;
    
    public UsuarioController () {
        try {
            usuarioDao = new UsuarioBancoDao();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha na Conexão com o Banco", 
                    "SEVERAL ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean salvar(Usuario user) throws ClassNotFoundException, IOException, SQLException {
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
    public boolean atualizar(Usuario user) throws ClassNotFoundException, IOException, SQLException {        
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
            throws ClassNotFoundException, IOException, SQLException, AtualizacaoUsuarioInvalidaException {
        
        if (! (validarSenha(user.getId(), user.getPassword())))
            throw new AtualizacaoUsuarioInvalidaException("Senha Antiga Invalida");
        
        return usuarioDao.atualizar(user);
    }
    
    public boolean atualizarSafe(Usuario user, String novaSenha, String antigaSenha) 
            throws ClassNotFoundException, IOException, SQLException, AtualizacaoUsuarioInvalidaException {
        
        if (! (validarSenha(user.getId(), antigaSenha)))
            throw new AtualizacaoUsuarioInvalidaException("Senha Antiga Invalida");
        
        user.setPassword(novaSenha);
        return usuarioDao.atualizar(user);
    }
    
    private boolean validarSenha(int Id, String password) throws ClassNotFoundException, IOException, SQLException {
        Usuario user = usuarioDao.getById(Id);
        
        return (user.getPassword().equals(password));
    }
    
}
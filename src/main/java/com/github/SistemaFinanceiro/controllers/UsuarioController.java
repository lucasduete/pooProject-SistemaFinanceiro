
package com.github.SistemaFinanceiro.controllers;

import com.github.SistemaFinanceiro.interfaces.DaoInterface;
import com.github.SistemaFinanceiro.dao.UsuarioBancoDao;
import com.github.SistemaFinanceiro.exceptions.AtualizacaoUsuarioInvalidaException;
import com.github.SistemaFinanceiro.interfaces.AutenticacaoInterface;
import com.github.SistemaFinanceiro.model.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

    public class UsuarioController implements DaoInterface<Usuario>, AutenticacaoInterface{
    
    public UsuarioBancoDao bancoDao;
    
    public UsuarioController () {
        try {
            bancoDao = new UsuarioBancoDao();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha na Conexão com o Banco", 
                    "SEVERAL ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean salvar(Usuario user) throws ClassNotFoundException, IOException, SQLException {
        return bancoDao.salvar(user);
    }

    @Override
    public List<Usuario> listar() throws ClassNotFoundException, IOException, SQLException {
        return bancoDao.listar();
    }

    @Override
    public boolean remover(Usuario user) throws ClassNotFoundException, IOException, SQLException {
        return bancoDao.remover(user);
    }

    @Override
    @Deprecated
    public boolean atualizar(Usuario user) throws ClassNotFoundException, IOException, SQLException {        
        return bancoDao.atualizar(user);
    }

    @Override
    public int userLogin(String email, String password) throws IOException, ClassNotFoundException, SQLException {
        return bancoDao.userLogin(email, password);
    }
    
    public boolean atualizarSafe(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, AtualizacaoUsuarioInvalidaException {
        
        if (! (validarSenha(user.getId(), user.getPassword())))
            throw new AtualizacaoUsuarioInvalidaException("Senha Antiga Invalida");
        
        return bancoDao.atualizar(user);
    }
    
    public boolean atualizarSafe(Usuario user, String novaSenha, String antigaSenha) 
            throws ClassNotFoundException, IOException, SQLException, AtualizacaoUsuarioInvalidaException {
        
        if (! (validarSenha(user.getId(), antigaSenha)))
            throw new AtualizacaoUsuarioInvalidaException("Senha Antiga Invalida");
        
        user.setPassword(novaSenha);
        return bancoDao.atualizar(user);
    }
    
    private boolean validarSenha(int Id, String password) throws ClassNotFoundException, IOException, SQLException {
        Usuario user = bancoDao.getUsuario(Id);
        
        return (user.getPassword().equals(password));
    }
    
    public Usuario getUsuario(int Id) throws ClassNotFoundException, IOException, SQLException {
        return bancoDao.getUsuario(Id);
    }
    
}
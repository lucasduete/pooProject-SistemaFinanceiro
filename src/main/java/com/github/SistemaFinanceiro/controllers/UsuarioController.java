
package com.github.SistemaFinanceiro.controllers;

import com.github.SistemaFinanceiro.interfaces.DaoInterface;
import com.github.SistemaFinanceiro.dao.UsuarioBancoDao;
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
    public boolean atualizar(Usuario user) throws ClassNotFoundException, IOException, SQLException {
        return bancoDao.atualizar(user);
    }

    @Override
    public int userLogin(String email, String password) throws IOException, ClassNotFoundException, SQLException {
        return bancoDao.userLogin(email, password);
    }
    
}
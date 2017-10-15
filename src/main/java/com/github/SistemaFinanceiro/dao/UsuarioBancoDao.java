
package com.github.SistemaFinanceiro.dao;

import com.github.SistemaFinanceiro.model.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioBancoDao implements DaoInterface<Usuario>{

    private Connection conn;
    
    
    public UsuarioBancoDao () {
        try {
            conn = ConFactory.getConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha na Conexão com o Banco", 
                    "SEVERAL ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    @Override
    public boolean salvar(Usuario user) throws IOException, ClassNotFoundException, SQLException{
        String sql = "INSERT INTO usuario (Email, Nome, DataNasc, Sexo, Password) VALUES (?,?,?,?,?);";
        
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getNome());
        stmt.setDate(3, Date.valueOf(user.getDataNasc()));
        stmt.setString(4, user.getSexo());
        stmt.setString(5, user.getPassword());


        if(stmt.executeUpdate() > 0) {
            stmt.close();
            conn.close();
            return true;
        } else {
            stmt.close();
            conn.close();
            return false;
        }
    }
    
    
    @Override
    public List<Usuario> listar() throws IOException, ClassNotFoundException, SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM Usuario;";
        PreparedStatement stmt = conn.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            usuarios.add(new Usuario(
                    rs.getInt("Id"),
                    rs.getString("Email"),
                    rs.getString("Nome"),
                    rs.getDate("DataNasc").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    rs.getString("Sexo"),
                    rs.getString("Password")
            ));
        }
        
        return usuarios;
    }
    
    
    @Override
    public boolean atualizar(Usuario user) throws IOException, ClassNotFoundException, SQLException {
        String sql = "UPDATE Usuario SET Email = ?, Nome = ?, DataNasc = ?, "
                + "Sexo = ?, Password = ? WHERE ID = ?";
                
        PreparedStatement stmt = conn.prepareStatement(sql);
            
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getNome());
        stmt.setDate(3, Date.valueOf(user.getDataNasc()));
        stmt.setString(4, user.getSexo());
        stmt.setString(5, user.getPassword());
        stmt.setInt(6, user.getId());
            
        if(stmt.executeUpdate() > 0) {
            stmt.close();
            conn.close();
            return true;
        } else {
            stmt.close();
            conn.close();
            return false;
        }
    }
    
    
    @Override
    public boolean remover(Usuario user) throws IOException, ClassNotFoundException, SQLException {
        String sql = "DELETE Usuario WHERE ID = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, user.getId());

        if(stmt.executeUpdate() > 0) {
            stmt.close();
            conn.close();
            return true;
        } else {
            stmt.close();
            conn.close();
            return false;
        }
    }
}
   



package com.github.SistemaFinanceiro.controllers;

import BancoDeDados.ConFactory;
import com.github.SistemaFinanceiro.model.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class UsuarioBancoDeDadosDao implements Dao<Usuario>{

    private Connection con;
    /**
     * Construtor UsuarioDao sem argumento serve para 
     *fazer a conexão com o banco de dados.
     * @author Lucas e Kaíque
     * @version 1.0
     * */
    public UsuarioBancoDeDadosDao () {
        try {
            con = new ConFactory().getConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Falha na conexão com o banco",
                    "Mensagem de Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Metodo para salvar um novo usuario no banco de dados
     * @param obj objeto usuario
     * @return 
     * @throws Execeção caso o email e/ou password estejam errado
     * @author Lucas e Kaíque
     * @version 1.0
     * */
    @Override
    public boolean salvar(Usuario obj) throws IOException, ClassNotFoundException, SQLException{
        PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO usuario (email,password)"
                + " VALUES (?,?,?)");

        stmt.setString(1, obj.getEmail());
        stmt.setString(2, obj.getPassword());


        return stmt.executeUpdate() > 0;
    }
    /**
     * Metodo sem argumento que serve para listar 
     * todos os usuarios salvos no banco de dados
     * @return 
     * @throws Execeção caso a lista esteja vazia
     * @author Lucas e Kaíque
     * @version 1.0
     * */
    @Override
    public List<Usuario> listar() throws IOException, ClassNotFoundException, SQLException {
            List<Usuario> usuarios = new ArrayList<>();

        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM xxxxxx");

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            Usuario p = new Usuario();
            p.setEmail(rs.getString(1));
            p.setPassword(rs.getString(2));
   
        }
        
        return usuarios;
    }
    /**
     * Metodo para atualizar um usuario no banco de dados
     * @param obj objeto usuario
     * @return 
     * @throws Execeção caso o email e/ou password estejam errado
     * @author Lucas e Kaíque
     * @version 1.0
     * */
    @Override
    public boolean atualizar(Usuario obj) throws IOException, ClassNotFoundException, SQLException {
      PreparedStatement stmt = con.prepareStatement(
                    "UPDATE xxxxx SET xxxxx = ? WHERE xxxx like ?");
            
            stmt.setString(1,obj.getEmail() );
            stmt.setString(2, obj.getPassword() );
            
           return stmt.executeUpdate() > 0;
}
    /**
     * Metodo para deletar um usuario no banco de dados
     * @param obj objeto usuario
     * @return um boolean
     * @throws Execeção caso o email e/ou password estejam errado
     * @author Lucas e Kaíque
     * @version 1.0
     * */
    @Override
    public boolean remover(Usuario obj) throws IOException, ClassNotFoundException, SQLException {
        PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM xxxx WHERE xxxxx like ?");
            
            stmt.setString(1, "xxxx");
            return true;
    }
}
   


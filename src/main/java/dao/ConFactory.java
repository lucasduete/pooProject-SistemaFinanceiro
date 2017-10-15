/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lucasduete
 */
public class ConFactory {
    private final String host;
    private final String user;
    private final String password;
    
    public ConFactory() {
        host = "jdbc:postgresql://localhost:5432/POO";
        user = "postgres";
        password = "postgres";
    }
    
    public Connection getConexao() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        
        return DriverManager.getConnection(host, user, password);
    }
    
}

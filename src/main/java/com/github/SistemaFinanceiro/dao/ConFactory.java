package com.github.SistemaFinanceiro.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConFactory {
    private static final String url = "jdbc:postgresql://localhost:5432/sistema_financeiro";
    private static final String usuario = "postgres";
    private static final String senha = "postgres";
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, usuario, senha);
    }

}

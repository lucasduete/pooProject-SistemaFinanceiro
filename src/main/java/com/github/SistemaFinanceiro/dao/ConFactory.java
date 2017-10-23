package com.github.SistemaFinanceiro.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta Classe Encapsula a Conexao com o Banco de Dados Gerando um Objeto
 * sql.Connection para se Utilizado Pelo Sistema.
 * @author Lucas Duete
 * @version 1.0
 * @since 8.0
 */

public class ConFactory {
    private static final String url = "jdbc:postgresql://localhost:5432/sistema_financeiro";
    private static final String usuario = "postgres";
    private static final String senha = "postgres";
    
    /**
     * Metodo Que Realiza a Conexao com o Banco de Dados.
     * @return Objeto do Tipo sql.Connection que Encapsula a Conexao com o Banco de Dados.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, usuario, senha);
    }

}

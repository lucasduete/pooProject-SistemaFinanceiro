package com.github.SistemaFinanceiro.dao;

import com.github.SistemaFinanceiro.interfaces.AutenticacaoInterface;
import com.github.SistemaFinanceiro.interfaces.DaoInterface;
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

/**
 * Esta Classe Comtem os Metodos que Encapsulam o Acesso ao Banco de Dados 
 * Realizando Assim todo o CRUD para o Objeto MovimentacaoFinanceira. 
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */

public class UsuarioBancoDao implements DaoInterface<Usuario>, AutenticacaoInterface{

    private final Connection conn;
    
    /**
     * Construtor Padrao da Classe MovimentacaoBancoDao Onde e Instanciada a um Objeto do 
     * Tipo Connection para Realizar as Conexao com o Banco de Dados.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo. 
     */
    
    public UsuarioBancoDao () throws ClassNotFoundException, SQLException {
        conn = ConFactory.getConnection();
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Adiçao de um novo 
     * Usuario no Banco de Dados.
     * @param user Objeto do Tipo Usuario que Contem os Dados de um Usuario
     * que Sera Salvo no Banco de Dados.
     * @return True se Salvou com Sucesso no Banco, False se Ocorreu Falha ao Salvar No Banco
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo.
     */
    
    @Override
    public boolean salvar(Usuario user) throws IOException, ClassNotFoundException, SQLException{
        String sql = "INSERT INTO usuario (Email, Nome, DataNasc, Sexo, Password) VALUES (?,?,?,?,?);";
        
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getNome());
        stmt.setDate(3, Date.valueOf(user.getDataNasc()));
        stmt.setString(4, user.getSexo());
        stmt.setString(5, user.getPassword());


        boolean aux = (stmt.executeUpdate() > 0);
        
        stmt.close();
        conn.close();
        
        return aux;
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Listagem de Todos 
     * os Usuarios Salvos no Banco de Dados.
     * @return ArrayList de Objetos Usuario, Retorna uma Lista Vazia Caso Nao Haja 
     * Nenhuma Usuario Salvo.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo
     */
    
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
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Atualizaçao de um 
     * Usuario no Banco de Dados.
     * @param user Objeto do Tipo Usuario que Contem os novos dados 
     * que Seram Atualizados no Banco de Dados.
     * @return True se Atualizou com Sucesso no Banco, False se Ocorreu Falha ao Atualizar No Banco
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo.
     */
    
    @Override
    public boolean atualizar(Usuario user) throws IOException, ClassNotFoundException, SQLException {
        String sql = "UPDATE Usuario SET (Email = ?, Nome = ?, DataNasc = ?, "
                + "Sexo = ?, Password = ?) WHERE ID = ?";
                
        PreparedStatement stmt = conn.prepareStatement(sql);
            
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getNome());
        stmt.setDate(3, Date.valueOf(user.getDataNasc()));
        stmt.setString(4, user.getSexo());
        stmt.setString(5, user.getPassword());
        stmt.setInt(6, user.getId());
            
        boolean aux = (stmt.executeUpdate() > 0);
        
        stmt.close();
        conn.close();
        return aux;
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Remoçao de um Usuario 
     * no Banco de Dados.
     * @param user Objeto do Tipo Usuario que Contem o Usuario que sera Removido 
     * do Banco de Dados (Unica Informaçao Realmente Relevante E O Seu Id).
     * @return True se Removeu com Sucesso no Banco, False se Ocorreu Falha ao Remover No Banco.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo.
     */
    
    @Override
    public boolean remover(Usuario user) throws IOException, ClassNotFoundException, SQLException {
        String sql = "DELETE FROM Usuario WHERE ID = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, user.getId());

        boolean aux = (stmt.executeUpdate() > 0);
        
        stmt.close();
        conn.close();
        
        return aux;
    }

    @Override
    public int userLogin(String email, String password) throws IOException, ClassNotFoundException, SQLException {
        int aux = -1;
        String sql = "SELECT Id FROM Usuario WHERE Email ILIKE ? AND Password ILIKE ?;";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, password);
        
        ResultSet rs = stmt.executeQuery();
        
        if(rs.next()) 
            aux = rs.getInt("Id");
        
        rs.close();
        stmt.close();
        conn.close();
        
        return aux;
    }
}
   


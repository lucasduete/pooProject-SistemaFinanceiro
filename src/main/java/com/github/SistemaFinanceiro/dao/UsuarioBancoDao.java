package com.github.SistemaFinanceiro.dao;

import com.github.SistemaFinanceiro.interfaces.UserDaoInterface;
import com.github.SistemaFinanceiro.model.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta Classe Comtem os Metodos que Encapsulam o Acesso ao Banco de Dados 
 * Realizando Assim todo o CRUD para o Objeto MovimentacaoFinanceira. 
 * @author Lucas Duete e Kaique Augusto
 * @version 1.2
 * @since 8.0
 */

public class UsuarioBancoDao implements UserDaoInterface {

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
            Date data = rs.getDate("DataNasc");
            Instant instant = Instant.ofEpochMilli(data.getTime());
            usuarios.add(new Usuario(
                    rs.getInt("Id"),
                    rs.getString("Email"),
                    rs.getString("Nome"),
                    LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate(),
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
        String sql = "UPDATE Usuario SET Email = ?, Nome = ?, DataNasc = ?, "
                + "Sexo = ?, Password = ? WHERE ID = ?";
                
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
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Busca por um 
     * Usuario Especifico Salvo no Banco de Dados.
     * @param idUsuario Variavel Inteira que Contem o ID do Usuario do Qual Sera Retornado as 
     * suas informaçoes.
     * @return Objeto do Tipo Usuario Preenchido com as Informaçoes Acossiadas a Id Informada.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo
     */
    
    @Override
    public Usuario getById(int idUsuario) throws IOException, ClassNotFoundException, SQLException {
        Usuario user = new Usuario();

        String sql = "SELECT * FROM Usuario WHERE Id = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, idUsuario);

        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            Date data = rs.getDate("DataNasc");
            Instant instant = Instant.ofEpochMilli(data.getTime());
            user.setId(rs.getInt("Id"));
            user.setEmail(rs.getString("Email"));
            user.setNome(rs.getString("Nome"));
            user.setDataNasc(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate());
            user.setSexo(rs.getString("Sexo"));
            user.setPassword(rs.getString("Password"));
        }
        
        return user;
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Atutenticaçao 
     * de um Usuario com Base no Banco de Dados.
     * @param email String que Contem o Email do Usuario que Esta Tentando Realizar Login
     * @param password String que Contem a Senha do Usuario que Esta Tentando Realizar Login
     * @return Int que Contem o Id do Usuario que Fez Login, Caso Seja -1 Entao as Credenciais 
     * sao Invalidas.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo.
     */

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
   


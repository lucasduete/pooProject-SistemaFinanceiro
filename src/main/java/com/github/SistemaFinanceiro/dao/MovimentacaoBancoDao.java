package com.github.SistemaFinanceiro.dao;

import com.github.SistemaFinanceiro.interfaces.DaoInterface;
import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
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
 * @version 1.1
 * @since 8.0
 */
public class MovimentacaoBancoDao implements DaoInterface<MovimentacaoFinanceira> {
    
    private final Connection conn;
    
    /**
     * Construtor Padrao da Classe MovimentacaoBancoDao Onde e Instanciada a um Objeto do 
     * Tipo Connection para Realizar as Conexao com o Banco de Dados.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo. 
     */
    
    public MovimentacaoBancoDao() throws ClassNotFoundException, SQLException {
        conn = ConFactory.getConnection();
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Adiçao de uma Nova Movimentacao 
     * Financeira no Banco de Dados.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceira que Contem os Dados da Movimentacao 
     * que Sera Salva no Banco de Dados.
     * @return True se Salvou com Sucesso no Banco, False se Ocorreu Falha ao Salvar No Banco
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo.
     */

    @Override
    public boolean salvar(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        String sql = "INSERT INTO Movimentacao_Financeira(Descricao, Data, Valor, Tipo, Categoria, IDUsuario) "
                + "VALUES (?,?,?,?,?,?)";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, movimentacao.getDescricao());
        stmt.setDate(2, Date.valueOf(movimentacao.getData()));
        stmt.setDouble(3, movimentacao.getValor());
        stmt.setString(4, movimentacao.getTipo());
        stmt.setString(5, movimentacao.getCategoria());
        stmt.setInt(6, movimentacao.getIdUsuario());
        
        boolean aux = (stmt.executeUpdate() > 0);
        stmt.close();
        conn.close();
            
        return aux;
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Listagem de Todas 
     * as Movimentacoes Financeiras Salvas no Banco de Dados.
     * @return ArrayList de Objetos MovimentacaoFinanceira, Retorna uma Lista Vazia Caso Nao Haja 
     * Nenhuma Movimentacao Salva.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo
     * @deprecated
     */

    @Override
    @Deprecated
    public List<MovimentacaoFinanceira> listar() throws ClassNotFoundException, IOException, SQLException {                
        String sql = "SELECT * FROM Movimentacao_Financeira";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        return getMovimentacoes(stmt);
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Remoçao de uma Movimentacao 
     * Financeira no Banco de Dados.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceira que Contem a Movimentacao que sera 
     * Removida do Banco de Dados (Unica Informaçao Realmente Relevante E O Seu Id).
     * @return True se Removeu com Sucesso no Banco, False se Ocorreu Falha ao Remover No Banco.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo.
     */

    @Override
    public boolean remover(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        String sql = "DELETE FROM Movimentacao_Financeira WHERE ID = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, movimentacao.getId());
        
        boolean aux = (stmt.executeUpdate() > 0);
        stmt.close();
        conn.close();
        
        return aux;
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Atualizaçao de uma Movimentacao 
     * Financeira no Banco de Dados.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceira que Contem os novos dados 
     * que Seram Atualizados no Banco de Dados.
     * @return True se Atualizou com Sucesso no Banco, False se Ocorreu Falha ao Atualizar No Banco
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo.
     */

    @Override
    public boolean atualizar(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        String sql = "UPDATE Movimentacao_Financeira SET Descricao = ?, Data = ?, Valor = ?, "
                + "Tipo = ?, Categoria = ? WHERE ID = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, movimentacao.getDescricao());
        stmt.setDate(2, Date.valueOf(movimentacao.getData()));
        stmt.setDouble(3, movimentacao.getValor());
        stmt.setString(4, movimentacao.getTipo());
        stmt.setString(5, movimentacao.getCategoria());
        stmt.setInt(6, movimentacao.getId());
        
        boolean aux = (stmt.executeUpdate() > 0);
        stmt.close();
        conn.close();
        
        return aux;
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Busca por uma 
     * Movimentacao Financeira Especifica Salva no Banco de Dados.
     * @param idMovimentacao Variavel Inteira que Contem o ID da Movimentaçao da Qual Sera Retornada as 
     * informaçoes.
     * @return Objeto do Tipo MovimentacaoFinanceira Preenchido com as Informaçoes 
     * Acossiadas a Id Informada.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo
     */
    
    @Override
    public MovimentacaoFinanceira getById(int idMovimentacao) 
                throws ClassNotFoundException, IOException, SQLException {
                
        String sql = "SELECT * FROM Movimentacao_Financeira WHERE Id = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idMovimentacao);
        
        return getMovimentacoes(stmt).get(0);
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco Realizando a Operaçao de Listagem de Todas 
     * as Referentes a um Usuario Movimentacoes Financeiras Salvas no Banco de Dados.
     * @param idUsuario Variavel Inteira que Contem o ID do Usuario do Qual Seram Listadas as 
     * Movimentacoes Financeiras.
     * @return ArrayList de Objetos MovimentacaoFinanceira, Retorna uma Lista Vazia Caso Nao Haja 
     * Nenhuma Movimentacao Salva.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo
     */
    
    public List<MovimentacaoFinanceira> listarByUsuario(int idUsuario) 
                throws ClassNotFoundException, IOException, SQLException {
                
        String sql = "SELECT * FROM Movimentacao_Financeira WHERE IDUsuario = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idUsuario);
        
        return getMovimentacoes(stmt);
    }
    
    /**
     * Este Metodo Encapsula a Geraçao de Uma Lista de Movimentacoes Financeiras com Base na 
     * Execuçao de uma PreparedStatement. Este Metodo Esta Acessivel Apenas a Esta Classe.
     * @param stmt Objeto do Tipo PreparedStatement Que Contem a Statement que Sera Executada no 
     * ResultSet.
     * @return ArrayList de Objetos do Tipo MovimentacaoFinanceira com Base no Statement.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Nunca e Disparada, Necessaria por Implementar a Interface DaoInterface.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Conexao com o 
     * Banco de Dados ou uma Operaçao no Mesmo
     */
    
    private List<MovimentacaoFinanceira> getMovimentacoes(PreparedStatement stmt) 
                throws ClassNotFoundException, IOException, SQLException {
    
        List<MovimentacaoFinanceira> movimentacoes = new ArrayList<>();
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            Date data = rs.getDate("Data");
            Instant instant = Instant.ofEpochMilli(data.getTime());
            movimentacoes.add(new MovimentacaoFinanceira(
                    rs.getInt("Id"),
                    rs.getString("Descricao"),
                    LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate(),
                    rs.getDouble("Valor"),
                    rs.getString("Tipo"),
                    rs.getString("Categoria"),
                    rs.getInt("IdUsuario")
            ));
        }
       
        stmt.close();
        conn.close();
        return movimentacoes;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucas Duete e Kaique Augusto
 */
public class MovimentacaoBancoDao implements DaoInterface<MovimentacaoFinanceira> {
    
    private final Connection conn;
    
    public MovimentacaoBancoDao() throws ClassNotFoundException, SQLException {
        conn = new ConFactory().getConexao();
    }

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
        
        if (stmt.executeUpdate() > 0) {
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
    @Deprecated
    public List<MovimentacaoFinanceira> listar() throws ClassNotFoundException, IOException, SQLException {                
        String sql = "SELECT * FROM Movimentacao_Financeira";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        return getMovimentacoes(stmt);
    }

    @Override
    public boolean remover(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        String sql = "DELETE FROM Movimentacao_Financeira WHERE ID = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setInt(1, movimentacao.getId());
        
        if (stmt.executeUpdate() > 0) {
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
    public boolean atualizar(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        String sql = "UPDATE Movimentacao_Financeira SET (Descricao = ?, Data = ?, Valor = ?, "
                + "Tipo = ?, Categoria = ?) WHERE ID = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, movimentacao.getDescricao());
        stmt.setDate(2, Date.valueOf(movimentacao.getData()));
        stmt.setDouble(3, movimentacao.getValor());
        stmt.setString(4, movimentacao.getTipo());
        stmt.setString(5, movimentacao.getCategoria());
        stmt.setInt(6, movimentacao.getId());
        
        if (stmt.executeUpdate() > 0) {
            stmt.close();
            conn.close();
            
            return true;
        } else {
            stmt.close();
            conn.close();
            
            return false;
        }
    }
    
    public List<MovimentacaoFinanceira> listarByUsuario(int idUsuario) 
                throws ClassNotFoundException, IOException, SQLException {
                
        String sql = "SELECT * FROM Movimentacao_Financeira WHERE IDUsuario = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idUsuario);
        
        return getMovimentacoes(stmt);
    }
    
    private List<MovimentacaoFinanceira> getMovimentacoes(PreparedStatement stmt) 
                throws ClassNotFoundException, IOException, SQLException {
    
        List<MovimentacaoFinanceira> movimentacoes = new ArrayList<>();
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            movimentacoes.add(new MovimentacaoFinanceira(
                    rs.getInt("Id"),
                    rs.getString("Descricao"),
                    rs.getDate("Data").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
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

package com.github.SistemaFinanceiro.controllers;

import java.time.LocalDate;
import java.util.List;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import com.github.SistemaFinanceiro.model.Usuario;
import dao.MovimentacaoBancoDao;
import java.io.IOException;
import java.sql.SQLException;

public class MovimentacaoController {
    
    private final MovimentacaoBancoDao bancoDao;
    
    public MovimentacaoController() throws ClassNotFoundException, SQLException {
         bancoDao = new MovimentacaoBancoDao();
    }
	
    //Criar uma Movimentaço nova
    public boolean salvarMovimentacao(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
        return bancoDao.salvar(movimentacao);
    }
	
	//Encontrar Movimentaçao com base na data
    public MovimentacaoFinanceira encontrarPorData(int idUsuario, LocalDate dataInicio, LocalDate dataFim) 
            throws ClassNotFoundException, IOException, SQLException{
            
        List<MovimentacaoFinanceira> movimentacoes = bancoDao.listarByUsuario(idUsuario);
            for(MovimentacaoFinanceira mf : movimentacoes) {
                if(mf.getData().isAfter(dataInicio) && mf.getData().isBefore(dataFim))
                    return mf;
            }
            
        return null;
	}
        
    
    public boolean atualizarMovimentacao(MovimentacaoFinanceira movimentacao)
                throws ClassNotFoundException, IOException, SQLException {
            return bancoDao.atualizar(movimentacao);
	}
	
	public boolean deletarMovimentacao(MovimentacaoFinanceira movimentacao) 
                    throws ClassNotFoundException, IOException, SQLException {
		return bancoDao.remover(movimentacao);
	}

}

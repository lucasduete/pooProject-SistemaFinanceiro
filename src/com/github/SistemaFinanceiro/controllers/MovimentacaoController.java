package com.github.SistemaFinanceiro.controllers;

import java.time.LocalDate;
import java.util.List;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import com.github.SistemaFinanceiro.model.Usuario;

public class MovimentacaoController {
	
	//Criar uma movimentação nova
	public MovimentacaoFinanceira criarMovimentacao(String descricao, LocalDate data, double valor, String tipo,
			String categoria) {
		MovimentacaoFinanceira fm = new MovimentacaoFinanceira(descricao, data, valor, tipo, categoria);
		return fm;
	}
	
	//Encontrar movimentação com base na data
	public MovimentacaoFinanceira encontrarPorData(List<MovimentacaoFinanceira> movimentacoes, LocalDate dataInicio, LocalDate dataFim) {
		for(MovimentacaoFinanceira mf : movimentacoes) {
			if(mf.getData().isAfter(dataInicio) && mf.getData().isBefore(dataFim))
				return mf;
		}
		return null;
	}
	
	
	public boolean atualizarMovimentacao(Usuario user, MovimentacaoFinanceira fm, String descricao, LocalDate data, Double valor,
			String tipo, String categoria) {
		List<MovimentacaoFinanceira> movimentacoes = user.getMovimentacoes();
		
		for(int i = 0; i < movimentacoes.size(); i++) {
			if(movimentacoes.get(i).equals(fm)) {
				if(descricao != null) {
					fm.setDescricao(descricao);
				}
				
				if(data != null) {
					fm.setData(data);
				}
				
				if(valor == null) {
					fm.setValor(valor);
				}
				
				if(tipo != null) {
					fm.setTipo(tipo);
				}
				
				if(categoria != null) {
					fm.setCategoria(categoria);
				}
				return true;
			}
		}
		return false;		
	}
	
	public boolean deletarMovimentacao(Usuario user, MovimentacaoFinanceira mf) {
		List<MovimentacaoFinanceira> movimentacoes = user.getMovimentacoes();
		movimentacoes.remove(mf);
		user.setMovimentacoes(movimentacoes);
		return false;
	}

}

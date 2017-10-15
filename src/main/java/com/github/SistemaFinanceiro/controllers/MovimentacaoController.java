package com.github.SistemaFinanceiro.controllers;

import java.time.LocalDate;
import java.util.List;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import com.github.SistemaFinanceiro.model.Usuario;

public class MovimentacaoController {
	
	//Criar uma Movimentaço nova
	public MovimentacaoFinanceira criarMovimentacao(String descricao, LocalDate data, double valor, String tipo,
			String categoria) {
		MovimentacaoFinanceira fm = new MovimentacaoFinanceira(0, descricao, data, valor, tipo, categoria, 0);
		return fm;
	}
	
	//Encontrar Movimentaçao com base na data
	public MovimentacaoFinanceira encontrarPorData(List<MovimentacaoFinanceira> movimentacoes, LocalDate dataInicio, LocalDate dataFim) {
		for(MovimentacaoFinanceira mf : movimentacoes) {
			if(mf.getData().isAfter(dataInicio) && mf.getData().isBefore(dataFim))
				return mf;
		}
		return null;
	}
	
	
	public boolean atualizarMovimentacao(Usuario user, MovimentacaoFinanceira fm, MovimentacaoFinanceira nova) {
		List<MovimentacaoFinanceira> movimentacoes = user.getMovimentacoes();
		
		for(int i = 0; i < movimentacoes.size(); i++) {
			if(movimentacoes.get(i).equals(fm)) {
				if(nova.getDescricao() != null) {
					fm.setDescricao(nova.getDescricao());
				}
				
				if(nova.getData() != null) {
					fm.setData(nova.getData());
				}
				
				if(nova.getValor() == null) {
					fm.setValor(nova.getValor());
				}
				
				if(nova.getTipo() != null) {
					fm.setTipo(nova.getTipo());
				}
				
				if(nova.getCategoria() != null) {
					fm.setCategoria(nova.getCategoria());
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

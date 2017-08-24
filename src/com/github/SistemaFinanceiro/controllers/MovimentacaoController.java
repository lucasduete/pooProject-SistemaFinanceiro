package com.github.SistemaFinanceiro.controllers;

import java.awt.List;
import java.time.LocalDate;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;

public class MovimentacaoController {
	
	public MovimentacaoFinanceira criarMovimentacao(String descricao, LocalDate data, double valor, String tipo,
			String categoria) {
		MovimentacaoFinanceira fm = new MovimentacaoFinanceira(descricao, data, valor, tipo, categoria);
		return fm;
	}
	
	public MovimentacaoFinanceira encontrarPorData(LocalDate dataInicio, LocalDate dataFim) {
		
	}
	
	public boolean atualizarMovimentacao(MovimentacaoFinanceira fm, String descricao, LocalDate data, double valor,
			String tipo, String categoria) {
		if(descricao != null) {
			fm.setDescricao(descricao);
		}
		
		if(data != null) {
			fm.setData(data);
		}
		
		if(valor != null) {
			fm.setValor(valor);
		}
		
		if(tipo != null) {
			fm.setTipo(tipo);
		}
		
		if(categoria != null) {
			fm.setCategoria(categoria);
		}
		
	}
	
	public boolean deletarMovimentacao() {
		
	}

}

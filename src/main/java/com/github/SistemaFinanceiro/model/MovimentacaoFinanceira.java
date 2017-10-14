package com.github.SistemaFinanceiro.model;

import java.io.Serializable;
import java.time.LocalDate;

public class MovimentacaoFinanceira implements Comparable<MovimentacaoFinanceira>, Serializable {
	
	private static int countFM;
	private final int id;
	private String descricao;
	private LocalDate data;
	private Double valor;
	private String tipo;
	private String Categoria;
	
	public MovimentacaoFinanceira(String descricao, LocalDate data, Double valor, String tipo,
			String categoria) {
		super();
		id = ++countFM;
		this.descricao = descricao;
		this.data = data;
		this.valor = valor;
		this.tipo = tipo;
		Categoria = categoria;
	}

    public MovimentacaoFinanceira() {
        id = ++countFM;
        descricao = null;
	data = null;
	valor = null;
        tipo = null;
        Categoria = null;
    }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Categoria == null) ? 0 : Categoria.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + id;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovimentacaoFinanceira other = (MovimentacaoFinanceira) obj;
		if (Categoria == null) {
			if (other.Categoria != null)
				return false;
		} else if (!Categoria.equals(other.Categoria))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id != other.id)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (Double.doubleToLongBits(valor) != Double.doubleToLongBits(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "movimentacaoFinanceira [id=" + id + ", descricao=" + descricao + ", valor=" + valor + ", tipo=" + tipo
				+ ", Categoria=" + Categoria + "]";
	}

	@Override
	public int compareTo(MovimentacaoFinanceira mf) {
		if (getDescricao().equals(mf.getDescricao()) && getData().equals(mf.getData())
				&& getValor().equals(mf.getValor()) && getTipo().equals(mf.getTipo())
				&& getCategoria().equals(mf.getCategoria())) {
			return 1;
		} else {
			return 0;
		}
	}
	
	

}
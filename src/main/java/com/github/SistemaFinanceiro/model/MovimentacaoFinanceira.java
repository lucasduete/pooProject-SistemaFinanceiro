package com.github.SistemaFinanceiro.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class MovimentacaoFinanceira implements Comparable<MovimentacaoFinanceira>, Serializable {

    private int id;
    private String descricao;
    private LocalDate data;
    private Double valor;
    private String tipo;
    private String categoria;
    private int idUsuario;
	
    public MovimentacaoFinanceira(int id, String descricao, LocalDate data, Double valor, String tipo, 
            String categoria, int idUsuario) {
        super();
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.idUsuario = idUsuario;
    }

    public MovimentacaoFinanceira() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.descricao);
        hash = 97 * hash + Objects.hashCode(this.data);
        hash = 97 * hash + Objects.hashCode(this.valor);
        hash = 97 * hash + Objects.hashCode(this.tipo);
        hash = 97 * hash + Objects.hashCode(this.categoria);
        hash = 97 * hash + this.idUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovimentacaoFinanceira other = (MovimentacaoFinanceira) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MovimentacaoFinanceira{" + "id=" + id + ", descricao=" + descricao + ", data=" + data + ", valor=" + valor + ", tipo=" + tipo + ", categoria=" + categoria + ", idUsuario=" + idUsuario + '}';
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

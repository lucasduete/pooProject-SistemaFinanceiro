package com.github.SistemaFinanceiro.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Comparable<Usuario>{
    static int contUser;
    private final int id;
    private String email;
    private String nome;
    private LocalDate dataNasc;
    private char sexo;
    private String password;
    private List<MovimentacaoFinanceira> movimentacoes;

    public Usuario(String email, String nome, LocalDate dataNasc,
    		char sexo, String password) {
		id = ++contUser;
		this.email = email;
		this.nome = nome;
		this.dataNasc = dataNasc;
		this.sexo = sexo;
		this.password = password;
		movimentacoes = new ArrayList<>();
	}
    
    public Usuario() {
		id = ++contUser;
		email = null;
		nome = null;
		dataNasc = null;
		sexo = 'u';
		password = null;
		movimentacoes = new ArrayList<>();
	}    

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public List<MovimentacaoFinanceira> getMovimentacoes() {
		return movimentacoes;
	}
	
	public MovimentacaoFinanceira getMovimentacao(int index) {
		return movimentacoes.get(index);
	}

	public void setMovimentacoes(List<MovimentacaoFinanceira> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}
	
	public void setMovimentacao(MovimentacaoFinanceira mf) {
		movimentacoes.add(mf);
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataNasc == null) ? 0 : dataNasc.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((movimentacoes == null) ? 0 : movimentacoes.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + sexo;
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
		Usuario other = (Usuario) obj;
		if (dataNasc == null) {
			if (other.dataNasc != null)
				return false;
		} else if (!dataNasc.equals(other.dataNasc))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (movimentacoes == null) {
			if (other.movimentacoes != null)
				return false;
		} else if (!movimentacoes.equals(other.movimentacoes))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (sexo != other.sexo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", email=" + email + ", nome=" + nome + ", dataNasc=" + dataNasc + ", sexo=" + sexo
				+ ", password=" + password + ", movimentacoes=" + movimentacoes + "]";
	}

	@Override
	public int compareTo(Usuario u) {
		if (this.equals(u)) {
			//São iguais
			return 0;
		} else if (getNome().equals(u.getNome())
				|| getEmail().equals(u.getEmail())
				|| getDataNasc().equals(u.getDataNasc())
				|| getId() == u.getId() 
				|| getMovimentacoes() == u.getMovimentacoes()
				|| getPassword().equals(u.getPassword())
				|| getSexo() == u.getSexo()) {
			//Se alguma coisa igual
			return 1;
		}
		//Se forem diferentes
		return -1;
	}
    	
}

package com.github.SistemaFinanceiro.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario implements Comparable<Usuario>, Serializable {
    
    private int id;
    private String email;
    private String nome;
    private LocalDate dataNasc;
    private char sexo;
    private String password;

    public Usuario(int id, String email, String nome, LocalDate dataNasc,
    		char sexo, String password) {
		this.id = id;
		this.email = email;
		this.nome = nome;
		this.dataNasc = dataNasc;
		this.sexo = sexo;
		this.password = password;
	}
    
    public Usuario () {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
        hash = 11 * hash + Objects.hashCode(this.email);
        hash = 11 * hash + Objects.hashCode(this.nome);
        hash = 11 * hash + Objects.hashCode(this.dataNasc);
        hash = 11 * hash + this.sexo;
        hash = 11 * hash + Objects.hashCode(this.password);
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
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.sexo != other.sexo) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.dataNasc, other.dataNasc)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", email=" + email + ", nome=" + nome + ", dataNasc=" + dataNasc + ", sexo=" + sexo + ", password=" + password + '}';
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
            || getPassword().equals(u.getPassword())
            || getSexo() == u.getSexo()) {
            
                //Se alguma coisa igual
                return 1;
	}
	
        //Se forem diferentes
	return -1;
	}
    	
}

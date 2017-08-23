package edu.com.ifpb.pooProjectSistemaFinanceiro.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModeloDeUsuario {
    static int contUser;
    private final int id;
    private String email;
    private String nome;
    private LocalDate dataNasc;
    private char sexo;
    private String password;
    private List<movimentacaoFinanceira> movimentacoes;

    public ModeloDeUsuario(String email, String nome, LocalDate dataNasc, char sexo) {
        this.id = ++contUser;
        this.email = email;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        movimentacoes = new ArrayList<>();
    } 

    public ModeloDeUsuario() {
        this.id = ++contUser;
        this.email = email;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.nome);
        hash = 83 * hash + Objects.hashCode(this.dataNasc);
        hash = 83 * hash + this.sexo;
        hash = 83 * hash + Objects.hashCode(this.password);
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
        final ModeloDeUsuario other = (ModeloDeUsuario) obj;
        if (this.sexo != other.sexo) {
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
        return "ModeloDeUsuario{" + "id=" + id + ", nome=" + nome + ", dataNasc=" + dataNasc + ", sexo=" + sexo + ", password=" + password + '}';
    }
    
    
}

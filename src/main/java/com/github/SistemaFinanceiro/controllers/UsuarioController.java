
package com.github.SistemaFinanceiro.controllers;

import com.github.SistemaFinanceiro.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    
    private static List<Usuario> contas;
    
    public UsuarioController () {
        contas = new ArrayList<>();
    }

    public List<Usuario> getContas() {
        return contas;
    }
    
    //Autenticando o Email e a Senha do usuario
    public boolean userLogin (String email, String password) {
        for(Usuario user : contas) {
           if(user.getEmail().equals(email) && user.getPassword().equals(password)){
               return true;
            }
        }
        return false;
    }  
    
    //Adicionando um novo usuario 
    public boolean adicionar(Usuario u) {
    	//Verifica se já usuario já esta cadastrado
        for(Usuario user : contas){
            if(user.compareTo(u) == 0) return false;
        }
        return contas.add(u);
    }
    
    public boolean adicionarByEmail(Usuario u) {
    	//Verifica se já tem um usuario com este email cadastrado
        for(Usuario user : contas){
            if(user.getEmail().equals(u.getEmail())) return false;
        }
        return contas.add(u);
    }
    
    //Atualizando dados do Usuario
    public boolean atualizar(Usuario user, int idUsuario) {
        for(int i = 0; i < contas.size(); i++) {
            if(contas.get(i).getId() == idUsuario) {
                contas.set(i, user);
                return true;
            }
        }
        return false;
    }
    
     public Usuario localizar(String email, String senha) {
        for (Usuario user : contas) {
            if (user.getEmail().equals(email) && user.getPassword().equals(senha)) {
                return user;
            }
        }
        return null;
    }
    
    
    //Remover conta de um Usuario
    public boolean removerConta (String email, String password){
        for (int i = 0; i < contas.size(); ++i) {
        	if (contas.get(i).getEmail().equals(email) &&
        			contas.get(i).getPassword().equals(password)) {
        		contas.remove(i);
        		return true;
        	}
                		
        }
    	return false;
    }
    
}

package edu.com.ifpb.pooProjectSistemaFinanceiro.controle;

import edu.com.ifpb.pooProjectSistemaFinanceiro.modelo.ModeloDeUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class usuarioController {
    
    private List<ModeloDeUsuario> contas;
    
    public usuarioController (){
        
        contas = new ArrayList<>();
    }

    public List<ModeloDeUsuario> getContas() {
        return contas;
    }
    
    //Autenticando o Email e a Senha do usuario
    public boolean userLogin (String email, String password){
        for(ModeloDeUsuario user : contas){
           if(user.getEmail().equals(email) && user.getPassword().equals(password)){
               return true;
            }
        }
        return false;
    }  
    
    //Adicionando um novo usuario 
    public boolean adicionar(ModeloDeUsuario u){
        for(ModeloDeUsuario user : contas){
            if(user.getEmail().equals(user.getEmail())) return false;
        }
        return contas.add(u);
    }
    
    //Atualizando dados do Usuario
    public boolean atualizar(ModeloDeUsuario user){
        for(int i=0; i<contas.size();i++){
            if(contas.get(i).getEmail().equals(user.getEmail())){
                contas.set(i,user);
                return true;
            }
        }
        return false;
    }
    
     public ModeloDeUsuario localizar(String email, String senha) {

        for (ModeloDeUsuario user : contas) {
            if (user.getEmail().equals(email) && user.getPassword().equals(senha)) {
                return user;
            }
        }
        return null;
    }
    
    
    //Remover conta de um Usuario
    public boolean removerConta (String email, String password){
       
        for (ModeloDeUsuario user : contas) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
               contas.remove(user);
            }
        }return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.contas);
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
        final usuarioController other = (usuarioController) obj;
        if (!Objects.equals(this.contas, other.contas)) {
            return false;
        }
        return true;
    }
    
     
    
}


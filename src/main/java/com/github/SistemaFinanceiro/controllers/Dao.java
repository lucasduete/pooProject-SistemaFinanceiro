
package com.github.SistemaFinanceiro.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao <T>{
    
    public boolean salvar(T obj) throws  IOException, ClassNotFoundException, SQLException;
    public List<T> listar() throws IOException, ClassNotFoundException, SQLException;
    public boolean atualizar(T obj) throws  IOException, ClassNotFoundException, SQLException;
    public boolean remover(T obj) throws  IOException, ClassNotFoundException, SQLException;
}

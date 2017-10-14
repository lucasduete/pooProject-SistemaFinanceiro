package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author lucasduete
 */
public interface DaoInterface<T> {
    
    public boolean salvar(T obj)  throws ClassNotFoundException, IOException, SQLException;
    public List<T> listar() throws ClassNotFoundException, IOException, SQLException;
    public boolean remover(Object T) throws ClassNotFoundException, IOException, SQLException;
    public boolean atualizar(T objVelho, T objNovo) throws ClassNotFoundException, IOException, SQLException;
    
}

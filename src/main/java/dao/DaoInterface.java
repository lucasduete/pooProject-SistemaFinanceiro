package dao;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author lucasduete
 */
public interface DaoInterface<T extends Serializable> {
    
    public final String PATHNAME = "./Backup";
    
    public boolean salvar(T obj)  throws ClassNotFoundException, IOException, SQLException;
    public List<T> listar() throws ClassNotFoundException, IOException, SQLException;
    public boolean remover(T obj) throws ClassNotFoundException, IOException, SQLException;
    public boolean atualizar(int id, T obj) throws ClassNotFoundException, IOException, SQLException;
    
}

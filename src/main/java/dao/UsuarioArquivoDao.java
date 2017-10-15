package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;

import com.github.SistemaFinanceiro.model.Usuario;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author lucasduete
 */
public class UsuarioArquivoDao implements DaoInterface<Usuario>{
    
    private final File diretorio;
    private File users;
    
    public UsuarioArquivoDao() {
        diretorio = new File(PATHNAME);
        users = new File(PATHNAME + "/users.bin");
        
        if (!diretorio.exists())
                diretorio.mkdir();
        
        if (!users.exists())
            try {
                users.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Falha no Backup do Banco",
                        "SEVERAL ERROR", JOptionPane.ERROR_MESSAGE);
            }
    }

    @Override
    public boolean salvar(Usuario user) throws ClassNotFoundException, IOException, SQLException {
        List<Usuario> usuarios = listar();
        
        if (usuarios.add(user)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(users));
            outputStream.writeObject(user);
            outputStream.close();
        
            return true;
        } else
            return false;
        
    }

    @Override
    public List<Usuario> listar() throws ClassNotFoundException, IOException, SQLException {        
        if (users.length() == 0)
            return new ArrayList<>();
        else {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(users));
            return (List<Usuario>) inputStream.readObject();
        }        
    }

    @Override
    public boolean remover(Usuario user) throws ClassNotFoundException, IOException, SQLException {
        List<Usuario> usuarios = listar();
        
        if(usuarios.remove(user)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(users));
            outputStream.writeObject(usuarios);
            outputStream.close();
            
            return true;
        } else 
            return false;
    }

    @Override
    public boolean atualizar(Usuario user) throws ClassNotFoundException, IOException, SQLException {
        List<Usuario> usuarios = listar();
        
        for(int i = 0; i < usuarios.size(); i++) {
            if(usuarios.get(i).getId() == user.getId()) {
                usuarios.set(i, user);
                
                ObjectOutputStream outputSteam = new ObjectOutputStream(new FileOutputStream(users));
                outputSteam.writeObject(usuarios);
                outputSteam.close();
                
                return true;
            }
        }
        
        return false;        
    }
    
}

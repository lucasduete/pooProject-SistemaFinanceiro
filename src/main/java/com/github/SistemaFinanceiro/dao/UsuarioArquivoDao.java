package com.github.SistemaFinanceiro.dao;

import com.github.SistemaFinanceiro.interfaces.DaoInterface;
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
 * Esta Classe Contem os Metodos que Encapsulam o Acesso aos Arquivos
 * de Backup, Realizando assim todo o CRUD em Arquivos Binarios para o 
 * Objeto Usuario.
 * @author Lucas Duete e Kaique Augusto.
 * @version 1.0
 * @since 8.0
 */
public class UsuarioArquivoDao implements DaoInterface<Usuario>{
    
    private final File diretorio;
    private File users;
    
    /**
     * Construtor da Classe, 
     * Neste Metodo sao definidas as variaveis diretorio e users 
     * contendo respectivamente a pasta de Backup e o Arquivo de 
     * Backup dos Usuarios. Neste metodo tambem sao verificados 
     * se tais Arquivos Existem e Caso Bao os Mesmos sao Criados.
     */
    
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
    
    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Adicao de um novo Usuario.
     * @param user E um Objeto do Tipo Usuario contendo o Usuario que sera Salvo.
     * @return true Se Salvo com sucesso, false Se  Nao Foi Possivel Salvar o Usuario.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */

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
    
    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Listagem dos Usuarios.
     * @return A Lista dos Usuarios que Estao Salvos no Arquivo de Backup.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */

    @Override
    public List<Usuario> listar() throws ClassNotFoundException, IOException, SQLException {        
        if (users.length() == 0)
            return new ArrayList<>();
        else {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(users));
            return (List<Usuario>) inputStream.readObject();
        }        
    }
    
    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Remoçao de um Usuario.
     * @param user E um Objeto do Tipo Usuario contendo o Usuario que sera Removido.
     * @return true Se Removido com sucesso, false Se  Nao Foi Possivel Remover o Usuario.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */

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
    
    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Atualizaçao de um Usuario.
     * @param user E um Objeto do Tipo Usuario contendo os novos dados do Usuario.
     * @return true Se Atualizado com sucesso, false Se  Nao Foi Possivel Atualizar o Usuario.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */

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

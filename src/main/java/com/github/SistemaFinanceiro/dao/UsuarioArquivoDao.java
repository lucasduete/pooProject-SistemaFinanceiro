package com.github.SistemaFinanceiro.dao;

import com.github.SistemaFinanceiro.exceptions.UniqueException;
import com.github.SistemaFinanceiro.interfaces.UserDaoInterface;
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

/**
 * Esta Classe Contem os Metodos que Encapsulam o Acesso aos Arquivos
 * de Backup, Realizando assim todo o CRUD em Arquivos Binarios para o 
 * Objeto Usuario.
 * @author Lucas Duete e Kaique Augusto.
 * @version 1.4
 * @since 8.0
 */
public class UsuarioArquivoDao implements UserDaoInterface {
    
    private final File diretorio;
    private final File users;
    
    /**
     * Construtor da Classe, 
     * Neste Metodo sao definidas as variaveis diretorio e users 
     * contendo respectivamente a pasta de Backup e o Arquivo de 
     * Backup dos Usuarios. Neste metodo tambem sao verificados 
     * se tais Arquivos Existem e Caso Bao os Mesmos sao Criados.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O ao Criar o Arquivo de Backups.
     */
    
    public UsuarioArquivoDao() throws IOException {
        diretorio = new File(PATHNAME);
        users = new File(PATHNAME + "/users.bin");
        
        if (!diretorio.exists())
                diretorio.mkdir();
        
        if (!users.exists())
                users.createNewFile();
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
    public boolean salvar(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, UniqueException {
        List<Usuario> usuarios = listar();
        
        int size = usuarios.size();
        user.setId (
                usuarios.get(size).getId() + 1
            );
        
        if(emailUnico(user.getEmail()))
            throw new UniqueException();
        
        if (usuarios.add(user)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(users));
            outputStream.writeObject(usuarios);
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
    public boolean atualizar(Usuario user) 
            throws ClassNotFoundException, IOException, SQLException, UniqueException {
        List<Usuario> usuarios = listar();
        
        if(emailUnico(user.getEmail()))
            throw new UniqueException();
        
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
    
    /**
     * Este Metodo Encapsula as Arquivos de Backup Realizando a Operaçao de Busca por um 
     * Usuario Especifico Salvo no Backup.
     * @param idUsuario Variavel Inteira que Contem o ID do Usuario do Qual Sera Retornado as 
     * suas informaçoes.
     * @return Objeto do Tipo Usuario Preenchido com as Informaçoes Acossiadas a Id Informada.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */
    
    @Override
    public Usuario getById(int idUsuario) throws IOException, ClassNotFoundException, SQLException {
        List<Usuario> usuarios = listar();
        
        for(Usuario user: usuarios) 
            if(user.getId() == idUsuario)
                return user;
        
        return null;
    }
    
    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Autenticaçao de um Usuario. 
     * @param email String que contem o Email do Usuario que Sera Autenticado
     * @param password String que contem a Senha do Usuario que Sera Autenticado
     * @return inteiro >= 0 Se Autenticado com sucesso sendo o retorno o ID do Usuario, 
     * -1 Se Credenciais Invalidas.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */

    @Override
    public int userLogin(String email, String password) throws IOException, ClassNotFoundException, SQLException {
        List<Usuario> users = listar();
        
        for (Usuario user: users)
            if (user.getEmail().equals(email) && user.getPassword().equals(password))
                return user.getId();
        
        return -1;
    }
    
    private boolean emailUnico(String email) throws IOException, ClassNotFoundException, SQLException {
        List<Usuario> users = listar();
        
        for (Usuario user: users)
            if (user.getEmail().equals(email))
                return false;        
        
        return true;
    }
    
}

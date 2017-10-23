package com.github.SistemaFinanceiro.interfaces;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Esta Interface Define Todos os Metodos que uma Classe de Controller de Dao 
 * em Backups e no Banco de Dados Deve Implementar Assim como Todos os 
 * Atributos que Elas Devem Conhecer.
 * @author Lucas Duete
 * @version 1.0
 * @since 8.0
 */
public interface DaoInterface<T extends Serializable> {
    
    public final String PATHNAME = "./Backup";
    
    /**
     * Este Metodo Realiza a Operaçao de Salvar de um Novo Objeto Tanto no Banco 
     * de Dados como no Backup em Arquivos Binarios.
     * @param obj Objeto que Contem os dados que seram Salvos.
     * @return True Se Foi Possivel Salvar o Objeto, False Se Nao foi Possivel Salvar o Objeto.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public boolean salvar(T obj) 
            throws ClassNotFoundException, IOException, SQLException;
    
    /**
     * Este Metodo Realiza a Operaçao de Listagem de Todas os Objetos Salvos 
     * no Banco de Dados e nos Backups.
     * @return ArrayList de Objetos, Retorna uma Lista Vazia Caso Nao Haja Nenhum Objeto Salvo.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */    
    
    public List<T> listar() throws ClassNotFoundException, IOException, SQLException;
    
    /**
     * Este Metodo Realiza a Operacao de Remocao de um Objeto Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios.
     * @param obj Objeto que sera Removido do Banco de Dados e Do Backup.
     * @return True Se Foi Possivel Deletar o Objeto, False Se Nao foi Possivel Remover o Objeto.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public boolean remover(T obj) throws ClassNotFoundException, IOException, SQLException;
    
    /**
     * Este Metodo Realiza a Operaçao de Atualizacao de um Objeto Tanto no Banco de Dados como no   
     * Backup em Arquivos Binarios.
     * @param obj Objeto que contem os novos dados para serem Atualizados no Banco de Dados e no Backup.
     * @return True Se Foi Possivel Atualizar o Objeto, False Se Nao foi Possivel Atualizar o Objeto.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public boolean atualizar(T obj) 
            throws ClassNotFoundException, IOException, SQLException;
    public T getById(int id) throws IOException, ClassNotFoundException, SQLException;
    
}

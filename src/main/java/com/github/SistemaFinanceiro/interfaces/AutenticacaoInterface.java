package com.github.SistemaFinanceiro.interfaces;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Esta Interface Define como Deve ser o Metodo de Login Para Os 
 * Objetos Que Possam Fazer Autenticaçao no Sistema.
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */
public interface AutenticacaoInterface {
    
    /**
     * Este Metodo Realiza a Operacao de Atutenticaçao de um Objeto Autenticavel Qualquer 
     * Usando os Dados Contidos no Banco de Dados e/ou no Backup em Arquivos Binarios.
     * @param email String que Contem o Email do Objeto.
     * @param password String que Contem a Senha do Objeto.
     * @return Variavel Inteira Contendo o ID do Objeto Logado, Caso Seja -1 Entao as Credenciais Sao
     * Invalidas.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public int userLogin (String email, String password) throws IOException, ClassNotFoundException, SQLException;
    
}

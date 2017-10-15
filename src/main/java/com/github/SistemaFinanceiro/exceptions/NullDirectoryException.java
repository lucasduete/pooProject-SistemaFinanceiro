package com.github.SistemaFinanceiro.exceptions;

/*
 * Esta Classe Define um Exception de Tratamento Obrigatorio
 * que e Gerada ao Tentar Acessar o Diretorio de Backups porem
 * o mesmo nao existe
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */

public class NullDirectoryException extends Exception {
    
    /**
     * Construtor da Classe Com Parametros onde Recebe uma Mensagem
     * e Chama o Construtor de Exception com esta Mensagem
     * @param msg e a Mensagem que sera Informada no StackTrace
     */
    public NullDirectoryException(String msg) {
        super(msg);
    }
    
    /**
     * Construtor da Classe Sem Parametros onde
     * invoca-se o Construtor de Exception com uma mensagem padrao
     * que sera Informada no StackTrace
     */
    public NullDirectoryException() {
        super("Diretorio de Backups nao encontrado.");
    }
    
}

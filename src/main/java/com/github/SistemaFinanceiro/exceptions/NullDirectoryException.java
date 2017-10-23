package com.github.SistemaFinanceiro.exceptions;

/*
 * Esta Classe Define um Exception de Tratamento Obrigatorio
 * que e Gerada ao Tentar Acessar o Diretorio de Backups porem o mesmo foi
 * Tornado Inacessivel em Tempo de Execuçao da Aplicaçao.
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */

public class NullDirectoryException extends Exception {
    
    /**
     * Construtor Padrao de NullDirectoryException que Chama o Construtor 
     * da Super Classe Usando uma Mensagem Fornecida por que Instanciar a Classe.
     * @param msg Mensagem Informada Por Quem Instancia a Classe e Sera Repassada ao 
     * Contrutor da Super Classe.
     */
    
    public NullDirectoryException(String msg) {
        super(msg);
    }
    
    /**
     * Construtor Padrao de NullDirectoryException que Chama o Construtor 
     * da Super Classe Usando uma Mensagem Padrao que Define a Exception.
     */
    
    public NullDirectoryException() {
        super("Diretorio de Backups nao encontrado.");
    }
    
}

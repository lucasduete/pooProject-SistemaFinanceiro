package com.github.SistemaFinanceiro.exceptions;

/**
 * Esta Classe Define uma Exception de de Tratamento Obrigatorio que Define o 
 * Funcionamento de uma Exeçao Gerada ao Quando Nao E Possivel Acesso ao Servidor de 
 * Banco de Dados e nem aos Backups.
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */
public class FailDaoException extends Exception {
    
    /**
     * Construtor Padrao de FailDaoException que Chama o Construtor 
     * da Super Classe Usando uma Mensagem Fornecida por que Instanciar a Classe.
     * @param msg Mensagem Informada Por Quem Instancia a Classe e Sera Repassada ao 
     * Contrutor da Super Classe.
     */

    public FailDaoException(String msg) {
        super(msg);
    }
    
    /**
     * Construtor Padrao de FailDaoException que Chama o Construtor 
     * da Super Classe Usando uma Mensagem Padrao que Define a Exception.
     */
    
    public FailDaoException() {
        super("Erro Critico em Todos os Serviços de Dados, verifique o Banco de Dados "
                + "e os Backups.");
    }
    
}


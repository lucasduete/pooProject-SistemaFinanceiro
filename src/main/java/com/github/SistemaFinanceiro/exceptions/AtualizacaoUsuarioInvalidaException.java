package com.github.SistemaFinanceiro.exceptions;

/**
 * Esta Classe Define uma Exception de de Tratamento Opcional que Define o 
 * Funcionamento de uma Exeçao Gerada ao Tentar Atualizar um Usuario com Dados 
 * Invalidos.
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */
public class AtualizacaoUsuarioInvalidaException extends RuntimeException {
    
    /**
     * Construtor Padrao de AtualizacaoUsuarioInvalidaException que Chama o Construtor 
     * da Super Classe Usando uma Mensagem Fornecida por que Instanciar a Classe.
     * @param msg 
     */

    public AtualizacaoUsuarioInvalidaException(String msg) {
        super(msg);
    }
    
    /**
     * Construtor Padrao de AtualizacaoUsuarioInvalidaException que Chama o Construtor 
     * da Super Classe Usando uma Mensagem Padrao que Define a Exception.
     */
    
    public AtualizacaoUsuarioInvalidaException() {
        super("Atualizçao com Dados Invalidos.");
    }
    
}


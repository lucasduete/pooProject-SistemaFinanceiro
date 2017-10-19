package com.github.SistemaFinanceiro.exceptions;

/**
 * 
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */
public class AtualizacaoUsuarioInvalidaException extends RuntimeException {

    public AtualizacaoUsuarioInvalidaException(String msg) {
        super(msg);
    }
    
    public AtualizacaoUsuarioInvalidaException() {
        super("Atualizçao com Dados Invalidos.");
    }
    
}


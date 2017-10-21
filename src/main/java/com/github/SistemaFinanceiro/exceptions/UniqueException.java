/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.SistemaFinanceiro.exceptions;

/**
 * 
 * @author lucasduete
 */
public class UniqueException extends RuntimeException {

    public UniqueException(String msg) {
        super(msg);
    }
    
    public UniqueException() {
        super("Email ja Cadastrado.");
    }
    
}


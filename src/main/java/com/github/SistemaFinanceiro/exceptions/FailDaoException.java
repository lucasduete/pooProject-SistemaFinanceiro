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
public class FailDaoException extends Exception {

    public FailDaoException(String msg) {
        super(msg);
    }
    
    public FailDaoException() {
        super("Erro Critico em Todos os Serviços de Dados, verifique o Banco de Dados "
                + "e os Backups.");
    }
    
}


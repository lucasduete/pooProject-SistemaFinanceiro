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
public class NullDirectoryException extends Exception {
    
    public NullDirectoryException(String msg) {
        super(msg);
    }
    
    public NullDirectoryException() {
        super("Diretorio de Backups nao encontrado.");
    }
    
}

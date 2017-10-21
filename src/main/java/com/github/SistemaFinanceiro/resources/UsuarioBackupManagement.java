/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.SistemaFinanceiro.resources;

import com.github.SistemaFinanceiro.interfaces.SGBDErrosInterface;

/**
 * 
 * @author lucasduete
 */
public abstract class FileManagementUsuario implements SGBDErrosInterface {
    
    public static boolean OP_INSERT = false;
    public static boolean OP_UPDATE = false;
    public static boolean OP_REMOVE = false;

}


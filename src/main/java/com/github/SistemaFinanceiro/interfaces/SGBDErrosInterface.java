/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.SistemaFinanceiro.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucasduete
 */
public interface SGBDErrosInterface {
    
    public final String ERROR_GERAL = "FATAL";
    public final List<String> ERRORS_SPECIFIC = new ArrayList<>();
    public final String Error1 = "FATAL: database \"sistema_financeiro\" does not exist";
    public final String Error2 = "FATAL: password authentication failed for user";
    public final String Error3 = "refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections";

}

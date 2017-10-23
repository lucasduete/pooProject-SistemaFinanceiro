package com.github.SistemaFinanceiro.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta Interface Define Todos os Metodos que uma Classe de Controller de SGBD 
 * e Backups Deve Implementar Assim como Todos os Atributos que Elas Devem 
 * Conhecer.
 * @author Lucas Duete
 * @version 1.0
 * @since 8.0
 */
public interface SGBDErrosInterface {
    
    public final String ERROR_GERAL = "FATAL";
    public final List<String> ERRORS_SPECIFIC = new ArrayList<>();
    public final String Error1 = "FATAL: database \"sistema_financeiro\" does not exist";
    public final String Error2 = "FATAL: password authentication failed for user";
    public final String Error3 = "refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections";

}

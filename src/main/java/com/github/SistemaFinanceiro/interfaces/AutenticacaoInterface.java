package com.github.SistemaFinanceiro.interfaces;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Esta Interface Defini como Deve ser o Metodo de Login
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */
public interface AutenticacaoInterface {
    
    public int userLogin (String email, String password) throws IOException, ClassNotFoundException, SQLException;
    
}

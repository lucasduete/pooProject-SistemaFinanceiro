/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.SistemaFinanceiro.interfaces;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author lucasduete
 * @version 1.1
 * @since 8.0
 */
public interface MovimentacaoDaoInterface extends DaoInterface<MovimentacaoFinanceira> {
    
    public List<MovimentacaoFinanceira> listarByUsuario(int idUsuario) 
            throws ClassNotFoundException, IOException, SQLException;
    
    
    
}

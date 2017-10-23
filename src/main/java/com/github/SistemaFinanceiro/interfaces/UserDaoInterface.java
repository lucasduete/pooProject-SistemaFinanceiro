package com.github.SistemaFinanceiro.interfaces;

import com.github.SistemaFinanceiro.model.Usuario;

/**
 * Esta Interface Define Todos os Metodos que um  Objeto do Tipo Usuario 
 * deve Implementar Obrigatoriamente.
 * @author Lucas Duete
 * @version 1.0
 * @since 8.0
 */

public interface UserDaoInterface extends DaoInterface<Usuario>, AutenticacaoInterface {
    
}

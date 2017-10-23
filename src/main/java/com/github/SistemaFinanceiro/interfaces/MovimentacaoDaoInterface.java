package com.github.SistemaFinanceiro.interfaces;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Esta Interface Define Todos os Metodos que um  Objeto do Tipo Movimentacao 
 * Financeira deve Implementar Obrigatoriamente.
 * @author Lucas Duete
 * @version 1.0
 * @since 8.0
 */
public interface MovimentacaoDaoInterface extends DaoInterface<MovimentacaoFinanceira> {
    
    /**
     * Este Metodo Lista todas as Movimentacoes Cadastradas de um Usuario.
     * @param idUsuario Variavel Inteira que contem o Id do Usuario do qual sera procurado as 
     * movimentacoes.
     * @return ArrayList de Objetos do Tipo MovimentacaoFinanceira deste Usuario, Tal 
     * Lista retorna Vazia quando Nao Existem Movimentacoes Cadastradas para Tal Usuario.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public List<MovimentacaoFinanceira> listarByUsuario(int idUsuario) 
            throws ClassNotFoundException, IOException, SQLException;
    
}

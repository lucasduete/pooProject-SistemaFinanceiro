package com.github.SistemaFinanceiro.controllers;

import java.time.LocalDate;
import java.util.List;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import dao.MovimentacaoBancoDao;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Esta Classe Encapsula todos os Metodos de Controle de Movimentacoes 
 * Financeiras como o Metodo para Salvar e Listar por Data.
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */

public class MovimentacaoController {
    
    private final MovimentacaoBancoDao bancoDao;
    
    /**
     * Construtor Padrao da Classe MovimentacaoController Onde e Instanciada a um Objeto do 
     * Tipo MovimentacaoBancoDao para Realizar as Operacoes em Banco.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados. 
     */
    
    public MovimentacaoController() throws ClassNotFoundException, SQLException {
        bancoDao = new MovimentacaoBancoDao();
    }
	
    /**
     * Este Metodo Realiza a Salva de uma Nova Movimentacao Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceira que Contem a Movimentacao que 
     * sera Salva.
     * @return True Se Foi Possivel Salvar a Movimentacao, False Se Nao foi Possivel Salvar a Movimentacao.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public boolean salvarMovimentacao(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
        return bancoDao.salvar(movimentacao);
    }
    
    /**
     * Este Metodo Realiza a Uma Busca das Movimentacooes de um Usuario dentro de 
     * um Intervalo de Tempo Estabelecido pelo Mesmo.
     * @param idUsuario Variavel Inteira que contem o Id do Usuario do qual sera procurado as 
     * movimentacoes.
     * @param dataInicio Variavel do Tipo LocalDate que Indica o Valor Inicial do Intervalo de Busca.
     * @param dataFim Variavel do Tipo LocalDate que Indica o Valor Final do Intervalo de Busca.
     * @return Objeto do Tipo MovimentacaoFinanceira que Esta Neste Intervalo.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public MovimentacaoFinanceira encontrarPorData(int idUsuario, LocalDate dataInicio, LocalDate dataFim) 
            throws ClassNotFoundException, IOException, SQLException{
            
        List<MovimentacaoFinanceira> movimentacoes = bancoDao.listarByUsuario(idUsuario);
        
        for(MovimentacaoFinanceira mf : movimentacoes) {
            if(mf.getData().isAfter(dataInicio) && mf.getData().isBefore(dataFim))
                return mf;
        }
            
        return null;
    }
        
    /**
     * Este Metodo Realiza a Atualizacao de uma Movimentacao Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceira que contem os novos dados 
     * de uma Movimentacao para ser Atualizada no Banco de Dados e no Backup.
     * @return True Se Foi Possivel Atualizar a Movimentacao, False Se Nao foi Possivel Atualizar a Movimentacao.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public boolean atualizarMovimentacao(MovimentacaoFinanceira movimentacao)
                throws ClassNotFoundException, IOException, SQLException {
        return bancoDao.atualizar(movimentacao);
    }
	
    /**
     * Este Metodo Realiza a Remocao de uma Movimentacao Tanto no Banco de Dados como no 
     * Backup em Arquivos Binarios.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceira que contem a Movimentacao que sera Removida
     * do Banco de Dados e Do Backup.
     * @return True Se Foi Possivel Deletar a Movimentacao, False Se Nao foi Possivel Remover a Movimentacao.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public boolean deletarMovimentacao(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
	return bancoDao.remover(movimentacao);
    }

}

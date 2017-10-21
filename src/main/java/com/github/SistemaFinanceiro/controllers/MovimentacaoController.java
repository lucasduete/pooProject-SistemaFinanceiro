package com.github.SistemaFinanceiro.controllers;

import java.time.LocalDate;
import java.util.List;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import com.github.SistemaFinanceiro.dao.MovimentacaoBancoDao;
import com.github.SistemaFinanceiro.exceptions.FailDaoException;
import com.github.SistemaFinanceiro.exceptions.NullDirectoryException;
import com.github.SistemaFinanceiro.interfaces.MovimentacaoDaoInterface;
import com.github.SistemaFinanceiro.interfaces.SGBDErrosInterface;
import com.github.SistemaFinanceiro.resources.MovimentacaoBackupManagement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Esta Classe Encapsula todos os Metodos de Controle de Movimentacoes 
 * Financeiras como o Metodo para Salvar e Listar por Data.
 * @author Lucas Duete e Kaique Augusto
 * @version 2.2
 * @since 8.0
 */

public class MovimentacaoController implements MovimentacaoDaoInterface, SGBDErrosInterface {
    
    private MovimentacaoDaoInterface movimentacaoDao;
    private static boolean ERROR_BD = false;
    
    /**
     * Construtor Padrao da Classe MovimentacaoController Onde e Instanciada a um Objeto do 
     * Tipo MovimentacaoBancoDao para Realizar as Operacoes em Banco.
     * @param idUsuario Integer que Contem o ID do Usuario que Esta Realizando 
     * Operaçoes.
     */
    
    public MovimentacaoController(int idUsuario) {
        try {
            movimentacaoDao = new MovimentacaoBancoDao();
        } catch (ClassNotFoundException | SQLException ex) {
            
            try {
                instanciaError(ex, idUsuario);
                
            } catch (FailDaoException ex1) {
                JOptionPane.showMessageDialog(null, "Falha Total e Acesso aos Dados de Sistema",
                        "SEVERAL ERROR", JOptionPane.ERROR_MESSAGE);
            }
            
        }
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
    
    @Override
    public boolean salvar(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
        return movimentacaoDao.salvar(movimentacao);
    }
    
    /**
     * Este Metodo Realiza a Operaçao de Listagem de Todas as Movimentacoes F
     * inanceiras Salvas no Banco de Dados e nos Backups.
     * @return ArrayList de Objetos MovimentacaoFinanceira, Retorna uma Lista Vazia Caso Nao Haja 
     * Nenhuma Movimentacao Salva.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     * @deprecated Pois Lista Todas as Movimentaçoes de Todos os Usuarios.
     */

    @Override
    @Deprecated
    public List<MovimentacaoFinanceira> listar() 
            throws ClassNotFoundException, IOException, SQLException {                
        return movimentacaoDao.listar();
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
    
    @Override
    public boolean atualizar(MovimentacaoFinanceira movimentacao)
                throws ClassNotFoundException, IOException, SQLException {
        return movimentacaoDao.atualizar(movimentacao);
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
    
    @Override
    public boolean remover(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
	return movimentacaoDao.remover(movimentacao);
    }
    
    /**
     * Este Metodo Encapsula o Acesso ao Banco de Dados Retornando Todas as 
     * Informaçoes sobre uma Movimentacao Financeira Salva no Banco de Dados.
     * @param idMovimentacao Variavel Inteira que contem o Id da Movimentacao Financeira cuja 
     * qual Deseja-se as Informaçoes.
     * @return Objeto do Tipo MovimentacaoFinanceira Preenchida com Todas as Informaçoes Referentes 
     * aquela Movimentacao Salva no Banco com tal Id.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    @Override
    public MovimentacaoFinanceira getById(int idMovimentacao) 
            throws ClassNotFoundException, IOException, SQLException {        
        return movimentacaoDao.getById(idMovimentacao);        
    }
    
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
    
    @Override
    public List<MovimentacaoFinanceira> listarByUsuario(int idUsuario) 
            throws ClassNotFoundException, IOException, SQLException{
            
        return movimentacaoDao.listarByUsuario(idUsuario);
    }
    
    /**
     * Este Metodo Realiza a Uma Busca das Movimentacoes de um Usuario dentro de 
     * um Intervalo de Tempo Estabelecido pelo Mesmo.
     * @param idUsuario Variavel Inteira que contem o Id do Usuario do qual sera procurado as 
     * movimentacoes.
     * @param dataInicio Variavel do Tipo LocalDate que Indica o Valor Inicial do Intervalo de Busca, Caso Null nao 
     * ha Data de Inicio Retornando-se Todas as Movimentacoes Antes da Data de Final
     * @param dataFim Variavel do Tipo LocalDate que Indica o Valor Final do Intervalo de Busca, Caso Null nao 
     * ha Data de Termino Retornando-se Todas as Movimentacoes Depois da Data de Inicio.
     * @return ArrayList de Objetos do Tipo MovimentacaoFinanceira que Estam Neste Intervalo, Tal 
     * Lista retorna Vazia quando Nao Existem Movimentacoes Neste Intervalo de Tempo e Retorna Todas as 
     * Movimentacoes Caso DataInicio e DataFim Sejam Null.
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public List<MovimentacaoFinanceira> encontrarPorData(int idUsuario, LocalDate dataInicio, LocalDate dataFim) 
            throws ClassNotFoundException, IOException, SQLException{
            
        List<MovimentacaoFinanceira> transactions = movimentacaoDao.listarByUsuario(idUsuario);
        List<MovimentacaoFinanceira> movimentacoes = new ArrayList<>();
    
        
        if(dataInicio == null && dataFim == null) {
            movimentacoes = transactions;
            
        } else if (dataInicio == null) {
            for(MovimentacaoFinanceira mf : transactions) {
                if(mf.getData().isBefore(dataFim) || mf.getData().isEqual(dataFim))
                    movimentacoes.add(mf);
            }
            
        } else if (dataFim == null) {
            for(MovimentacaoFinanceira mf : transactions) {
                if(mf.getData().isAfter(dataInicio) || mf.getData().isEqual(dataInicio))
                    movimentacoes.add(mf);
            }
            
        } else {
            for(MovimentacaoFinanceira mf : transactions) {
                if((mf.getData().isAfter(dataInicio) || mf.getData().isEqual(dataFim)) 
                        && (mf.getData().isBefore(dataFim) || mf.getData().isEqual(dataInicio)))
                    movimentacoes.add(mf);
            }
        }
            
        return movimentacoes;
    }

    private void instanciaError(Exception ex, int idUsuario) throws FailDaoException {
        
        if (!ex.getMessage().contains(ERROR_GERAL))
                return;
        
        try {
            
            if (ERROR_BD == false) {
                JOptionPane.showMessageDialog(null, "Falha na Conexão com o Banco, Usando Backups",
                        "TEMPORAL ERROR", JOptionPane.INFORMATION_MESSAGE);
                ERROR_BD = true;
            }
            
            movimentacaoDao = new MovimentacaoBackupManagement(idUsuario);
            
        } catch (NullDirectoryException | IOException ex1) {
            throw new FailDaoException();
        }
    }
    
}

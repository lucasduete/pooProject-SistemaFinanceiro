package com.github.SistemaFinanceiro.controllers;

import com.github.SistemaFinanceiro.dao.MovimentacaoArquivoDao;
import java.time.LocalDate;
import java.util.List;

import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import com.github.SistemaFinanceiro.dao.MovimentacaoBancoDao;
import com.github.SistemaFinanceiro.exceptions.FailDaoException;
import com.github.SistemaFinanceiro.exceptions.NullDirectoryException;
import com.github.SistemaFinanceiro.interfaces.MovimentacaoDaoInterface;
import com.github.SistemaFinanceiro.interfaces.SGBDErrosInterface;
import com.github.SistemaFinanceiro.resources.MovimentacaoBackupManagement;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Esta Classe Encapsula todos os Metodos de Controle de Movimentacoes 
 * Financeiras como o Metodo para Salvar e Listar por Data.
 * @author Lucas Duete e Kaique Augusto
 * @version 2.5
 * @since 8.0
 */

public class MovimentacaoController implements MovimentacaoDaoInterface, SGBDErrosInterface {
    
    private MovimentacaoDaoInterface movimentacaoDao;
    private static boolean ERROR_BD = false;
    private final int idUsuario;
    
    /**
     * Construtor Padrao da Classe MovimentacaoController Onde e Instanciada a um Objeto do 
     * Tipo MovimentacaoDao para Realizar as Operacoes em Banco e, em Caso de Erro, 
     * Gerencia-se o BackupManagement.
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
            
        } finally {
            this.idUsuario = idUsuario;
        }
    }
	
    /**
     * Este Metodo Realiza a Operaçao de Salvar de uma Nova Movimentacao Tanto no Banco de 
     * Dados como no Backup em Arquivos Binarios.
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
        
        if(!ERROR_BD)
            if(movimentacaoDao.salvar(movimentacao))
                try {
                    return new MovimentacaoArquivoDao(idUsuario).salvar(movimentacao);
                } catch (NullDirectoryException ex) {
                    return false;
                }
        
        return movimentacaoDao.salvar(movimentacao);
    }
    
    /**
     * Este Metodo Realiza a Operaçao de Listagem de Todas as Movimentacoes Financeiras 
     * Salvas no Banco de Dados e nos Backups.
     * @return ArrayList de Objetos do TipoMovimentacaoFinanceira, Retorna uma Lista Vazia Caso 
     * Nao Haja Nenhuma Movimentacao Salva.
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
     * Este Metodo Realiza a Operaçao de Atualizacao de uma Movimentacao Tanto no Banco de Dados 
     * como no Backup em Arquivos Binarios.
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
        if(!ERROR_BD)
            if(movimentacaoDao.atualizar(movimentacao))
                try {
                    return new MovimentacaoArquivoDao(idUsuario).atualizar(movimentacao);
                } catch (NullDirectoryException ex) {
                    return false;
                }
        
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
	if(!ERROR_BD)
            if(movimentacaoDao.remover(movimentacao))
                try {
                    return new MovimentacaoArquivoDao(idUsuario).remover(movimentacao);
                } catch (NullDirectoryException ex) {
                    return false;
                }
        
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
    
    /**
     * Gera um Grafico do Tipo Pizza com Base numa Lista de Movimentacoes Financeiras Retornando 
     * o Endereço do Grafico Gerado.
     * @param movimentacoes Lista de Objetos do Tipo Movimentaçao Financeira Pelas Quais Sera 
     * Gerada o Grafico.
     * @param tipo String que Contem o Tipo de Grafico ("Entrada" para Grafico de um Relatorio de Entrada 
     * e "Saída" para Grafico de um Relatorio de Saida).
     * @return String Contendo o Endereço Para o Grafico Gerado
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public String gerarGrafico(List<MovimentacaoFinanceira> movimentacoes, String tipo) 
            throws SQLException, ClassNotFoundException, IOException {
        
        DefaultPieDataset dataSet = new DefaultPieDataset();
        double soma = 0;
        String graphicName = movimentacoes.hashCode() + tipo + ".png";
        
        for(MovimentacaoFinanceira mf: movimentacoes) {
            soma = 0;
            for (MovimentacaoFinanceira m: movimentacoes) {                
                if(m.getCategoria().equals(mf.getCategoria()) && m.getTipo().toLowerCase().equals(tipo.toLowerCase())) {
                    soma += m.getValor();
                }
            }
            
            if(mf.getTipo().toLowerCase().equals(tipo.toLowerCase()))
                dataSet.setValue(mf.getCategoria(), soma);
            
        }

        // cria o gráfico
        JFreeChart grafico = ChartFactory.createPieChart("Relatorio de " + tipo + "s", dataSet, true, true, false);
        
        OutputStream arquivo = new FileOutputStream(graphicName);
        ChartUtilities.writeChartAsPNG(arquivo, grafico, 245, 300);
        arquivo.close();
        
        return graphicName;
        
    }
    
    /**
     * Gera um Grafico do Tipo Pizza com Base numa Lista de Movimentacoes Financeira em um Intervalo 
     * de Tempo Retornando o Endereço do Grafico Gerado, Caso DataInicio Seja Null e Data Fim Nao e Gerado Grafico 
     * com Todas as Movimentacoes Antes da Data de Fim, Caso Data de Fim Seja Null mas Data Inicio  nao 
     * e Retornado Grafico com Tdas as Movimentacoes Depois da Data de Inicio, Caso Ambos Null e Gerado Grafico 
     * Com Todas as Movimentacoes e Caso Ambos Sejam Preenchidos e Gerado um Grafico Com as Movimentacoes Neste
     * Intervalo.
     * @param movimentacoes Lista de Objetos do Tipo Movimentaçao Financeira Pelas Quais Sera 
     * Gerada o Grafico.
     * @param tipo String que Contem o Tipo de Grafico ("Entrada" para Grafico de um Relatorio de Entrada
     * @param dataInicio LocalDate que Contem a Data de Inicio do Intervalo de Tempo Estipulado.
     * @param dataFim LocalDate que Contem a Data de Termino do Intervalo de Tempo Estipulado.
     * e "Saída" para Grafico de um Relatorio de Saida).
     * @return String Contendo o Endereço Para o Grafico Gerado
     * @throws ClassNotFoundException Disparada quando Nao Foi Possivel Encontrar um Bliblioteca Necessaria para 
     * a Aplicaçao.
     * @throws IOException Disparada quando Ocorre Erro ao Fazer o Backup da Operacao em Arquivos.
     * @throws SQLException Disparada quando Ocorre Erro ao Realizar a Operacao no Banco de Dados.
     */
    
    public String gerarGraficoPorData(List<MovimentacaoFinanceira> movimentacoes, String tipo, 
            LocalDate dataInicio, LocalDate dataFim) throws SQLException, ClassNotFoundException, IOException {
        
        DefaultPieDataset dataSet = new DefaultPieDataset();
        double soma = 0;
        String graphicName = movimentacoes.hashCode() + tipo + ".png";
        
        if (dataInicio == null && dataFim == null) {
            gerarGrafico(movimentacoes, tipo);
            return graphicName;
        }else if (dataInicio == null) {
            for(MovimentacaoFinanceira mf: movimentacoes) {
                soma = 0;
                for (MovimentacaoFinanceira m: movimentacoes) {
                    if(m.getCategoria().equals(mf.getCategoria()) 
                            && m.getTipo().toLowerCase().equals(tipo.toLowerCase())
                            && (mf.getData().isBefore(dataFim) || mf.getData().isEqual(dataFim))) {
                        soma += mf.getValor();
                    }
                }

                if(mf.getTipo().toLowerCase().equals(tipo.toLowerCase()))
                    dataSet.setValue(mf.getCategoria(), soma);
            }
        } else if (dataFim == null) {
            for(MovimentacaoFinanceira mf: movimentacoes) {
                soma = 0;
                for (MovimentacaoFinanceira m: movimentacoes) {
                    if(m.getCategoria().equals(mf.getCategoria()) 
                            && m.getTipo().toLowerCase().equals(tipo.toLowerCase())
                            && (mf.getData().isAfter(dataInicio) || mf.getData().isEqual(dataInicio))) {
                        soma += mf.getValor();
                    }
                }

                if(mf.getTipo().toLowerCase().equals(tipo.toLowerCase()))
                    dataSet.setValue(mf.getCategoria(), soma);
            }
        } else {
            for(MovimentacaoFinanceira mf: movimentacoes) {
                soma = 0;
                for (MovimentacaoFinanceira m: movimentacoes) {
                    if(m.getCategoria().equals(mf.getCategoria()) 
                            && m.getTipo().toLowerCase().equals(tipo.toLowerCase())
                            && (mf.getData().isAfter(dataInicio) || mf.getData().isEqual(dataFim)) 
                            && (mf.getData().isBefore(dataFim) || mf.getData().isEqual(dataInicio))
                    ) {
                        soma += mf.getValor();
                    }
                }

                if(mf.getTipo().toLowerCase().equals(tipo.toLowerCase()))
                    dataSet.setValue(mf.getCategoria(), soma);
            }
        }

        // cria o gráfico
        JFreeChart grafico = ChartFactory.createPieChart("Relatorio de " + tipo + "s", dataSet, true, true, false);
        
        OutputStream arquivo = new FileOutputStream(graphicName);
        ChartUtilities.writeChartAsPNG(arquivo, grafico, 245, 300);
        arquivo.close();
        
        return graphicName;
        
    }
    
    /**
     * Metodo Privado Que Instancia um BackupManagement Com Base na Analise de uma Exception.
     * @param ex Objeto do Tipo Exception que Contem a Exception Disparada que Sera Analizada.
     * @param idUsuario Variavel Inteira que Contem o Id do Usuario que Esta Instanciando o 
     * Controller.
     * @throws FailDaoException Disparada Quando Nao For Possivel Acessar Nem o Banco de Dados 
     * Nem os Arquivos de Backup.
     */
    
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

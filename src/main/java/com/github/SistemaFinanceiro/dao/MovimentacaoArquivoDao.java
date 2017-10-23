package com.github.SistemaFinanceiro.dao;

import com.github.SistemaFinanceiro.exceptions.NullDirectoryException;
import com.github.SistemaFinanceiro.interfaces.MovimentacaoDaoInterface;
import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Esta Classe Contem os Metodos que Encapsulam o Acesso aos Arquivos
 * de Backup, Realizando assim todo o CRUD em Arquivos Binarios para o 
 * Objeto MovimentacaoFinanceira.
 * @author Lucas Duete e Kaique Augusto
 * @version 2.0
 * @since 8.0
 */
public class MovimentacaoArquivoDao implements MovimentacaoDaoInterface {
    
    private final File diretorio;
    private final File transactions;
    
    /**
     * Construtor da Classe MovimentacaoArquivoDao que Recebe uma String com 
     * o nome do Usuario e cria um Backup das Movimentacoes Financeiras do Mesmo. 
     * Neste Metodo sao definidas as variaveis diretorio e users 
     * contendo respectivamente a pasta de Backup e o Arquivo de 
     * Backup dos Usuarios. Neste metodo tambem sao verificados 
     * se tais Arquivos Existem e Caso Bao os Mesmos sao Criados.
     * @param idUsuario Integer que Contem o ID do Usuario que Esta Realizando 
     * Operacoes sobre os Arquivos Binarios.
     * @throws NullDirectoryException Disparado Quando O Diretorio de Backups 
     * for Deletado em Tempo de Execuçao.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     */
    
    public MovimentacaoArquivoDao(Integer idUsuario) throws NullDirectoryException, IOException {
        diretorio = new File(PATHNAME);
        transactions = new File(PATHNAME + "/" + idUsuario.hashCode() + ".bin");
        
        if(!diretorio.exists())
            throw new NullDirectoryException("Diretorio Nao Encontrado ao Gerenciar Movimentacoes "
                    + "para usuario: " + idUsuario);
            
        if(!transactions.exists())
            transactions.createNewFile();       
    }
    
    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Adicao de uma Nova MovimentacaoFinanceira de um Usuario no Arquivo 
     * Binario.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceria que Sera Salvo no Arquivo Binario.
     * @return True Se Salvo com Sucesso, False Se  Nao Foi Possivel Salvar a Movimentacao.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */

    @Override
    public boolean salvar(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
        List<MovimentacaoFinanceira> movimentacoes = listar();
        
        int size = movimentacoes.size();
        
        if(size == 0)
            movimentacao.setId(1);
        else 
            movimentacao.setId(
                movimentacoes.get(size - 1).getId() + 1
            );
        
        if (movimentacoes.add(movimentacao)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(transactions));
            outputStream.writeObject(movimentacoes);
            outputStream.close();
            
            return true;
        } else
            return false;
    }
    
    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Listagem de Todas as Movimentacoes Financeiras de um Usuario no Arquivo 
     * Binario.
     * @return ArrayList de Objetos do Tipo MovimentacaoFinanceira que Contem Todas as Movimentacoes 
     * Financeiras que Estao no Arquivo Binario Referente a um Usuario.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */

    @Override
    public List<MovimentacaoFinanceira> listar() throws ClassNotFoundException, IOException, SQLException {
        if (transactions.length() == 0)
            return new ArrayList<>();
        
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(transactions));
        return (List<MovimentacaoFinanceira>) inputStream.readObject();        
    }
    
    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Remocao de uma MovimentacaoFinanceira de um Usuario no Arquivo 
     * Binario.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceria que Sera Removida do Arquivo Binario.
     * @return true Se Foi Removida com sucesso, false Se  Nao Foi Possivel Remover a Movimentacao.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */

    @Override
    public boolean remover(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
        List<MovimentacaoFinanceira> movimentacoes = listar();
        
        if(movimentacoes.remove(movimentacao)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(transactions));
            outputStream.writeObject(movimentacoes);
            outputStream.close();
            
            return true;
        } else
            return false;
    }

    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Atualizacao de uma MovimentacaoFinanceira de um Usuario no Arquivo 
     * Binario.
     * @param movimentacao Objeto do Tipo MovimentacaoFinanceria que Contem os Novos Dados a 
     * Serem Atualizados no Arquivo Binario.
     * @return true Se Foi Atualizada com sucesso, false Se  Nao Foi Possivel Atualizar a Movimentacao.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */
    
    @Override
    public boolean atualizar(MovimentacaoFinanceira movimentacao) 
            throws ClassNotFoundException, IOException, SQLException {
        List<MovimentacaoFinanceira> movimentacoes = listar();
       
        for(int i = 0; i < movimentacoes.size(); i++) {
            if(movimentacoes.get(i).getId() == movimentacao.getId()){
                movimentacoes.set(i, movimentacao);
                
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(transactions));
                outputStream.writeObject(movimentacoes);
                outputStream.close();
                
                return true;
            }                
        }
        
        return false;
    }
    
    /**
     * Este Metodo Encapsula as Arquivos de Backup Realizando a Operaçao de Busca por uma 
     * Movimentacao Financeira Especifica Salva no Backup.
     * @param idMovimentacao Variavel Inteira que Contem o ID da Movimentacao Financeira da Qual 
     * Sera Retornado as suas informaçoes.
     * @return Objeto do Tipo MovimentacaoFinanceira Preenchido com as Informaçoes 
     * Acossiadas a Id Informada.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     */
    
    @Override
    public MovimentacaoFinanceira getById(int idMovimentacao) 
            throws IOException, ClassNotFoundException, SQLException {
        List<MovimentacaoFinanceira> movimentacoes = listar();
        
        for(MovimentacaoFinanceira movimentacao: movimentacoes) 
            if(movimentacao.getId() == idMovimentacao)
                return movimentacao;
        
        return null;
    }
    
    
    /**
     * Este Metodo Encapsula o Acesso aos Arquivos de Backup Realizando a
     * Operacao de Listagem de Todas as Movimentacoes Financeiras de um Usuario no Arquivo 
     * Binario.
     * @param idUsuario Int que Possui o Id do Usuario que Tera suas Movimentacoes Retornadas.
     * @return ArrayList de Objetos do Tipo MovimentacaoFinanceira que Contem Todas as Movimentacoes 
     * Financeiras que Estao no Arquivo Binario Referente a um Usuario.
     * @throws ClassNotFoundException Disparado por Falta de Biblioteca.
     * @throws IOException Disparado Caso Haja Algum Erro de I/O.
     * @throws SQLException Nunca e Disparado, Necessario por Implementar a Interface DaoInteface.
     * @deprecated Pois possui a Mesma Funcionalidade do Metodo Listar() Desta Classe
     */
    
    @Override
    @Deprecated
    public List<MovimentacaoFinanceira> listarByUsuario(int idUsuario) 
            throws ClassNotFoundException, IOException, SQLException {
        return listar();
    }
    
    public String gerarGrafico(List<MovimentacaoFinanceira> movimentacoes, String tipo) 
            throws SQLException, FileNotFoundException, IOException {
        
        DefaultPieDataset dataSet = new DefaultPieDataset();
        double soma = 0;
        String graphicName = movimentacoes.hashCode() + tipo + ".png";
        
        for(MovimentacaoFinanceira mf: movimentacoes) {
            soma = 0;
            for (MovimentacaoFinanceira m: movimentacoes) {
                if(m.getCategoria().equals(mf.getCategoria()) && m.getTipo().toLowerCase().equals(tipo.toLowerCase())) {
                    soma += mf.getValor();
                }
            }
            
            dataSet.setValue(mf.getCategoria(), soma);
            
        }

        // cria o gráfico
        JFreeChart grafico = ChartFactory.createPieChart("Relatorio de " + tipo + "s", dataSet, true, true, false);
        
        OutputStream arquivo = new FileOutputStream(graphicName);
        ChartUtilities.writeChartAsPNG(arquivo, grafico, 245, 300);
        arquivo.close();
        
        return graphicName;
        
    }
    
    public String gerarGraficoPorData(List<MovimentacaoFinanceira> movimentacoes, String tipo, 
            LocalDate dataInicio, LocalDate dataFim) throws SQLException, FileNotFoundException, IOException {
        
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
    
}

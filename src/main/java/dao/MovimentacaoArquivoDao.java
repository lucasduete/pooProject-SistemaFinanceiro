package dao;

import com.github.SistemaFinanceiro.exceptions.NullDirectoryException;
import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Esta Classe Contem os Metodos que Encapsulam o Acesso aos Arquivos
 * de Backup, Realizando assim todo o CRUD em Arquivos Binarios para o 
 * Objeto MovimentacaoFinanceira.
 * @author Lucas Duete e Kaique Augusto
 * @version 1.0
 * @since 8.0
 */
public class MovimentacaoArquivoDao implements DaoInterface<MovimentacaoFinanceira> {
    
    private final File diretorio;
    private final File transactions;
    
    /**
     * Construtor da Classe MovimentacaoArquivoDao que Recebe uma String com 
     * o nome do Usuario e cria um Backup das Movimentacoes Financeiras do Mesmo. 
     * Neste Metodo sao definidas as variaveis diretorio e users 
     * contendo respectivamente a pasta de Backup e o Arquivo de 
     * Backup dos Usuarios. Neste metodo tambem sao verificados 
     * se tais Arquivos Existem e Caso Bao os Mesmos sao Criados.
     * @param nomeUser String que Contem o Nome do Usuario que Esta Realizando 
     * Operacoes sobre os Arquivos Binarios.
     */
    
    public MovimentacaoArquivoDao(String nomeUser) {
        diretorio = new File(PATHNAME);
        transactions = new File(PATHNAME + "/" + nomeUser.hashCode() + ".bin");
        
        
        try {
            if(!diretorio.exists())
                throw new NullDirectoryException("Diretorio Nao Encontrado ao Gerenciar Movimentacoes para "
                        + "usuario: " + nomeUser);
            
            if(!transactions.exists())
                transactions.createNewFile();
        
        } catch (NullDirectoryException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha no Backup do Banco",
                        "SEVERAL ERROR", JOptionPane.ERROR_MESSAGE);
        }
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
    public boolean salvar(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        List<MovimentacaoFinanceira> movimentacoes = listar();
        
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
    public boolean remover(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
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
    public boolean atualizar(MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
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
    
}

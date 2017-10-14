/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author lucasduete
 */
public class MovimentacaoArquivoDao implements DaoInterface<MovimentacaoFinanceira> {
    
    private final File diretorio;
    private final File transactions;
    
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

    @Override
    public List<MovimentacaoFinanceira> listar() throws ClassNotFoundException, IOException, SQLException {
        if (transactions.length() == 0)
            return new ArrayList<>();
        
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(transactions));
        return (List<MovimentacaoFinanceira>) inputStream.readObject();        
    }

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

    @Override
    public boolean atualizar(int idMovimentacao, MovimentacaoFinanceira movimentacao) throws ClassNotFoundException, IOException, SQLException {
        List<MovimentacaoFinanceira> movimentacoes = listar();
       
        for(int i = 0; i < movimentacoes.size(); i++) {
            if(movimentacoes.get(i).getId() == idMovimentacao){
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

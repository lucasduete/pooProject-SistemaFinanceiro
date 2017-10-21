package com.github.SistemaFinanceiro.view;

import com.github.SistemaFinanceiro.controllers.MovimentacaoController;
import com.github.SistemaFinanceiro.model.MovimentacaoFinanceira;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;import java.util.logging.Level;
import java.util.logging.Logger;
;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucas Duete e Kaique Augusto
 * @version 1.3
 * @since 8.0
 */
public class TelaEditaMovimentacao extends javax.swing.JFrame {
    
    private final int idUsuario;
    private final int idMovimentacao;

    /**
     * Creates new form TelaCadastraMovimentacao
     */
    public TelaEditaMovimentacao() {
        initComponents();
        idUsuario = -1;
        idMovimentacao = -1;
    }
    
    public TelaEditaMovimentacao(int idUsuario, int idMovimentacao) {
        initComponents();
        this.idUsuario = idUsuario;
        this.idMovimentacao = idMovimentacao;
        setMovimentacao();
    }
    
    private void setMovimentacao() {
        
        MovimentacaoController controller = new MovimentacaoController(idUsuario);
        
        MovimentacaoFinanceira movimentacao;
        
        try {
            movimentacao = controller.getById(idMovimentacao);
            
            descricaoMovimentacao.setText(movimentacao.getDescricao());
            dataMovimentacao.setDate(Date.valueOf(movimentacao.getData()));
            valorMovimentacao.setText(String.valueOf(movimentacao.getValor()));

            switch (movimentacao.getTipo()) {
                case "entrada":
                    tipoMovimentacao.setSelectedIndex(0);
                    break;
                case "saida":
                    tipoMovimentacao.setSelectedIndex(1);
                    break;
            }

            switch (movimentacao.getCategoria()) {
                case "alimentação":
                    categoriaMovimentacao.setSelectedIndex(0);
                    break;
                case "cartão de crédito":
                    categoriaMovimentacao.setSelectedIndex(1);
                    break;
                case "despesa doméstica - aluguel":
                    categoriaMovimentacao.setSelectedIndex(2);
                    break;
                case "despesa doméstica - água":
                    categoriaMovimentacao.setSelectedIndex(3);
                    break;
                case "despesa doméstica - luz":
                    categoriaMovimentacao.setSelectedIndex(4);
                    break;
                case "despesa doméstica - intenet":
                    categoriaMovimentacao.setSelectedIndex(5);
                    break;
                case "saúde":
                    categoriaMovimentacao.setSelectedIndex(6);
                    break;
                case "pessoal":
                    categoriaMovimentacao.setSelectedIndex(7);
                    break;
                case "outros":
                    categoriaMovimentacao.setSelectedIndex(8);
                    break;
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaEditaMovimentacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TelaEditaMovimentacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaEditaMovimentacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        descricaoMovimentacao = new javax.swing.JTextField();
        tipoMovimentacao = new javax.swing.JComboBox<>();
        categoriaMovimentacao = new javax.swing.JComboBox<>();
        dataMovimentacao = new com.toedter.calendar.JDateChooser();
        valorMovimentacao = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        salvarMovimentacao = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        excluir = new javax.swing.JButton();
        limparMovimentacao = new javax.swing.JButton();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ediçao de Movimentações");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setText("Descrição");

        jLabel2.setText("Data");

        jLabel3.setText("Valor");

        jLabel4.setText("Tipo");

        jLabel5.setText("Categoria");

        tipoMovimentacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrada", "Saída" }));

        categoriaMovimentacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alimentação", "Cartão de Crédito", "Despesa Doméstica - Aluguel", "Despesa Doméstica - Água", "Despesa Doméstica - Luz", "Despesa Doméstica - Intenet", "Saúde", "Pessoal", "Outros" }));

        valorMovimentacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jPanel1.setBackground(new java.awt.Color(195, 195, 195));

        salvarMovimentacao.setFont(new java.awt.Font("Open Sans", 3, 12)); // NOI18N
        salvarMovimentacao.setText("Salvar");
        salvarMovimentacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarMovimentacaoActionPerformed(evt);
            }
        });

        cancelar.setFont(new java.awt.Font("Open Sans", 3, 12)); // NOI18N
        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        excluir.setFont(new java.awt.Font("Open Sans", 3, 12)); // NOI18N
        excluir.setText("Excluir");
        excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salvarMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelar, excluir, salvarMovimentacao});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salvarMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelar)
                    .addComponent(excluir))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelar, excluir, salvarMovimentacao});

        limparMovimentacao.setFont(new java.awt.Font("Open Sans", 2, 12)); // NOI18N
        limparMovimentacao.setText("Limpar");
        limparMovimentacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparMovimentacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(valorMovimentacao)
                            .addComponent(tipoMovimentacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(categoriaMovimentacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dataMovimentacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(descricaoMovimentacao)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(limparMovimentacao))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descricaoMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(valorMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tipoMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(categoriaMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(limparMovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel3, jLabel4, jLabel5});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void salvarMovimentacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvarMovimentacaoActionPerformed
        // TODO add your handling code here:
        
        if(descricaoMovimentacao.getText().isEmpty() ||
                dataMovimentacao.getDate() == null ||
                valorMovimentacao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os Dados", "Dados Invalidos", JOptionPane.ERROR_MESSAGE);
        } else {
            
            MovimentacaoController controller = new MovimentacaoController(idUsuario);
            
            String descricao = descricaoMovimentacao.getText();
            LocalDate data = dataMovimentacao.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            String temp = valorMovimentacao.getText();
            temp = temp.replace(",", ".");
            Double valor = Double.parseDouble(temp);
            
            String tipo = tipoMovimentacao.getSelectedItem().toString();
            String categoria = categoriaMovimentacao.getSelectedItem().toString();
            
            MovimentacaoFinanceira movimentacao = new MovimentacaoFinanceira(idMovimentacao, descricao,
                    data, valor, tipo, categoria, idUsuario);
            
            try {
                controller.atualizar(movimentacao);
                
                JOptionPane.showMessageDialog(null, "Movimentaçao Atualiza com Sucesso.", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao Acessar Servidor de Backups.", "CRITICAL ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao Acessar Bibliotecas Criticas do Sistema", "CRITICAL ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao Acessar Servidor de Banco de Dados", "CRITICAL ERROR", JOptionPane.ERROR_MESSAGE);
            } 
            
        }
        
    }//GEN-LAST:event_salvarMovimentacaoActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void limparMovimentacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparMovimentacaoActionPerformed
        // TODO add your handling code here:
        descricaoMovimentacao.setText("");
        dataMovimentacao.setDate(null);
        valorMovimentacao.setText("");
        tipoMovimentacao.setSelectedIndex(0);
        categoriaMovimentacao.setSelectedIndex(0);        
    }//GEN-LAST:event_limparMovimentacaoActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancelarActionPerformed

    private void excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluirActionPerformed
        // TODO add your handling code here:
        
        MovimentacaoController controller = new MovimentacaoController(idUsuario);
        
            try {
                controller.remover(
                        new MovimentacaoFinanceira(idMovimentacao, null, 
                                null, null, null, null, idUsuario)
                );
                
                JOptionPane.showMessageDialog(null, "Movimentaçao Removida com Sucesso.", 
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao Acessar Servidor de Backups.", "CRITICAL ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao Acessar Bibliotecas Criticas do Sistema", "CRITICAL ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao Acessar Servidor de Banco de Dados", "CRITICAL ERROR", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_excluirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaEditaMovimentacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEditaMovimentacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEditaMovimentacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEditaMovimentacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEditaMovimentacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelar;
    private javax.swing.JComboBox<String> categoriaMovimentacao;
    private com.toedter.calendar.JDateChooser dataMovimentacao;
    private javax.swing.JTextField descricaoMovimentacao;
    private javax.swing.JButton excluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JButton limparMovimentacao;
    private javax.swing.JButton salvarMovimentacao;
    private javax.swing.JComboBox<String> tipoMovimentacao;
    private javax.swing.JFormattedTextField valorMovimentacao;
    // End of variables declaration//GEN-END:variables
}

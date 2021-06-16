package gui;

import backend.Disponibilidade;
import backend.Loja;
import backend.ProdutoLoja;
import backend.RepositorioProdutos;
import backend.Sistema;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class JanelaPesquisaPorCB extends JDialog {

    //Referencia ao Sistema
    private Sistema sistema;

    private Loja lojaLigada;

    private ListagemProdutosLoja listagem;

    public JanelaPesquisaPorCB(Sistema sistema, Loja lojaLigada, ListagemProdutosLoja listagem) {
        initComponents();
        this.sistema = sistema;
        this.lojaLigada = lojaLigada;
        this.listagem = listagem;

        //Configurações da janela
        this.setTitle(lojaLigada.getNome() + " | Procurar produto por código de barras");
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);                     
        this.setLocationRelativeTo(null);

    }

    public void procurarPorCB() {
        String codigoBarras = textFieldPesquisaPorCB.getText();
            if(lojaLigada.getRepositorioProdutos().existeProdutoLoja(codigoBarras)){
                JOptionPane.showMessageDialog(this, "A sua loja já possui um produto com esse codigo de barras!");
                return;
            }
        try {
            if(sistema.getRepositorioProdutos().existeProduto(codigoBarras)){
                this.setVisible(false);
                ProdutoLoja produtoLoja = new ProdutoLoja(sistema.getRepositorioProdutos().getProdutoByCodigoBarras(codigoBarras), lojaLigada, 0, Disponibilidade.NAO);
                JanelaDadosProdutoLoja janela = new JanelaDadosProdutoLoja(sistema, lojaLigada, "novoPL", produtoLoja, listagem);
                janela.setVisible(true);
                
            }else {
                this.setVisible(false);
                JanelaDadosProdutoLoja janela = new JanelaDadosProdutoLoja(sistema, lojaLigada, "novoP", null, listagem);
                janela.setVisible(true);   
            }
        } catch (RepositorioProdutos.ProdutoNaoExisteException ex) {           
        }
    }
    
    public void fechar(){
        dispose();
    }
    

    //Items da janelaDadosProdutos necessários para o administrador administrador
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonProcurar = new javax.swing.JButton();
        textFieldPesquisaPorCB = new javax.swing.JTextField();
        labelPesquisaPorCB = new javax.swing.JLabel();
        buttonFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        buttonProcurar.setText("Procurar");
        buttonProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonProcurarActionPerformed(evt);
            }
        });

        labelPesquisaPorCB.setText("Insira o código de barras do produto, para verificar se existe:");

        buttonFechar.setText("Fechar");
        buttonFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textFieldPesquisaPorCB)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(labelPesquisaPorCB)
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonProcurar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonFechar)
                        .addGap(5, 5, 5)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelPesquisaPorCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldPesquisaPorCB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonProcurar)
                    .addComponent(buttonFechar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonProcurarActionPerformed
        procurarPorCB();
    }//GEN-LAST:event_buttonProcurarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        fechar();
    }//GEN-LAST:event_formWindowClosing

    private void buttonFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFecharActionPerformed
        fechar();
    }//GEN-LAST:event_buttonFecharActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonFechar;
    private javax.swing.JButton buttonProcurar;
    private javax.swing.JLabel labelPesquisaPorCB;
    private javax.swing.JTextField textFieldPesquisaPorCB;
    // End of variables declaration//GEN-END:variables
}

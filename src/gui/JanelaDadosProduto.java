package gui;

import backend.Produto;
import backend.RepositorioProdutos;
import backend.Sistema;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class JanelaDadosProduto extends JDialog {

    //Referencia ao Sistema
    private Sistema sistema;
    //Referencia a ListagemProdutos
    private ListagemProdutos listagem;
    //Referencia o produto a editar/visualizar
    private Produto produto;

    public JanelaDadosProduto(Sistema sistema, Produto produto, ListagemProdutos listagem) {
        initComponents();
        this.sistema = sistema;
        this.produto = produto;
        this.listagem = listagem;

        //Configurações da janela
        this.setTitle("Dados do produto");
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //No caso de um registo novo
        if (novoRegisto()) {
            setTitle("Criação de novo Produto");
            textFieldCodigoBarras.requestFocus();
        } else {
            setTitle("Alteração de dados de Produto");
            textFieldCodigoBarras.setText(produto.getCodigoBarras());
            textFieldCodigoBarras.setEditable(false);
            textFieldMarca.setText(produto.getMarca());
            textFieldModelo.setText(produto.getModelo());
            textFieldCarateristicas.setText(produto.getCarateristicas());
            textFieldFamilia.setText(produto.getFamilia());
        }

    }

    public boolean novoRegisto() {
        return produto == null;
    }

    public void guardar() {
        if (novoRegisto() && textFieldCodigoBarras.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduza p.f. o codigo de barras do produto!");
            textFieldCodigoBarras.requestFocus();
            return;
        }

        if (textFieldMarca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduza p.f. a marca do produto!");
            textFieldMarca.requestFocus();
            return;
        }

        if (textFieldModelo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduza p.f. o modelo do produto!");
            textFieldModelo.requestFocus();
            return;
        }

        if (textFieldCarateristicas.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduza p.f. as carateristicas produto!");
            textFieldCarateristicas.requestFocus();
            return;
        }

        if (textFieldFamilia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduza p.f. a familia do produto!");
            textFieldFamilia.requestFocus();
            return;
        }

        String codigoBarras = textFieldCodigoBarras.getText();

        if (novoRegisto()) {
            textFieldCodigoBarras.setEditable(true);
            Produto produto = new Produto();
            produto.setCodigoBarras(textFieldCodigoBarras.getText());
            produto.setMarca(textFieldMarca.getText());
            produto.setModelo(textFieldModelo.getText());
            produto.setCarateristicas(textFieldCarateristicas.getText());
            produto.setFamilia(textFieldFamilia.getText());
            try {
                sistema.getRepositorioProdutos().adicionarProduto(produto);
            } catch (RepositorioProdutos.ProdutoJaExisteException ex) {
                JOptionPane.showMessageDialog(this, "Já existe um produto com esse codigo de barras");
                fechar();
                return;
            }
        } else {

            textFieldCodigoBarras.setEditable(false);
            textFieldCodigoBarras.setText(produto.getCodigoBarras());
            produto.setMarca(textFieldMarca.getText());
            produto.setModelo(textFieldModelo.getText());
            produto.setCarateristicas(textFieldCarateristicas.getText());
            produto.setFamilia(textFieldFamilia.getText());

        }
        if (listagem != null) {
            listagem.atualizar();
        }
        JOptionPane.showMessageDialog(this, "Registo guardado com sucesso.");
        fechar();

    }

    private void fechar() {
        dispose();
    }

    //Items da janelaDadosProdutos necessários para o administrador administrador
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelCodigoBarras = new javax.swing.JLabel();
        textFieldCodigoBarras = new javax.swing.JTextField();
        labelMarca = new javax.swing.JLabel();
        textFieldMarca = new javax.swing.JTextField();
        labelModelo = new javax.swing.JLabel();
        textFieldModelo = new javax.swing.JTextField();
        lableCarateristicas = new javax.swing.JLabel();
        textFieldCarateristicas = new javax.swing.JTextField();
        lableFamilia = new javax.swing.JLabel();
        textFieldFamilia = new javax.swing.JTextField();
        buttonGuardar = new javax.swing.JButton();
        buttonFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        labelCodigoBarras.setText("Código Barras:");

        labelMarca.setText("Marca:");

        labelModelo.setText("Modelo:");

        lableCarateristicas.setText("Carateristicas:");

        lableFamilia.setText("Família:");

        buttonGuardar.setText("Guardar");
        buttonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGuardarActionPerformed(evt);
            }
        });

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonGuardar)
                        .addGap(12, 12, 12)
                        .addComponent(buttonFechar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lableFamilia)
                            .addComponent(lableCarateristicas)
                            .addComponent(labelModelo)
                            .addComponent(labelMarca)
                            .addComponent(labelCodigoBarras))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldCarateristicas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCodigoBarras))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMarca)
                    .addComponent(textFieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelModelo)
                    .addComponent(textFieldModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lableCarateristicas)
                    .addComponent(textFieldCarateristicas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lableFamilia)
                    .addComponent(textFieldFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonGuardar)
                    .addComponent(buttonFechar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_buttonGuardarActionPerformed

    private void buttonFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFecharActionPerformed
        fechar();
    }//GEN-LAST:event_buttonFecharActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        fechar();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonFechar;
    private javax.swing.JButton buttonGuardar;
    private javax.swing.JLabel labelCodigoBarras;
    private javax.swing.JLabel labelMarca;
    private javax.swing.JLabel labelModelo;
    private javax.swing.JLabel lableCarateristicas;
    private javax.swing.JLabel lableFamilia;
    private javax.swing.JTextField textFieldCarateristicas;
    private javax.swing.JTextField textFieldCodigoBarras;
    private javax.swing.JTextField textFieldFamilia;
    private javax.swing.JTextField textFieldMarca;
    private javax.swing.JTextField textFieldModelo;
    // End of variables declaration//GEN-END:variables
}

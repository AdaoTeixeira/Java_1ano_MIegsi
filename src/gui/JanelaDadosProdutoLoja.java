package gui;

import backend.Loja;
import backend.Produto;
import backend.ProdutoLoja;
import backend.RepositorioProdutos;
import backend.RepositorioProdutosLoja;
import backend.Sistema;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class JanelaDadosProdutoLoja extends JDialog {

    //Referencia ao Sistema
    private Sistema sistema;
    //Referencia a ListagemProdutosLoja
    private ListagemProdutosLoja listagem;
    //Referencia o produtoLoja a editar/visualizar
    private ProdutoLoja produtoLoja;
    //Referencia a loja que está ligada
    private Loja lojaLigada;
    //Contêm o tipo de pedido de alteração / criaçao
    private String pedido;

    public JanelaDadosProdutoLoja(Sistema sistema, Loja lojaLigada, String pedido, ProdutoLoja produtoLoja, ListagemProdutosLoja listagem) {
        initComponents();
        this.sistema = sistema;
        this.lojaLigada = lojaLigada;
        this.pedido = pedido;
        this.produtoLoja = produtoLoja;
        this.listagem = listagem;

        //Configurações da janela
        this.setTitle(lojaLigada.getNome() + " | Dados do Produto");
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //No caso de um registo novo
        if (pedido.equalsIgnoreCase("novoP")) {
            setTitle("Criação de novo Produto");
            textFieldCodigoBarras.requestFocus();

        } else {
            setTitle("Alteração de dados de Produto");
            textFieldCodigoBarras.setText(produtoLoja.getProduto().getCodigoBarras());
            textFieldCodigoBarras.setEditable(false);
            textFieldMarca.setText(produtoLoja.getProduto().getMarca());
            textFieldMarca.setEditable(false);
            textFieldModelo.setText(produtoLoja.getProduto().getModelo());
            textFieldModelo.setEditable(false);
            textFieldCarateristicas.setText(produtoLoja.getProduto().getCarateristicas());
            textFieldCarateristicas.setEditable(false);
            textFieldFamilia.setText(produtoLoja.getProduto().getFamilia());
            textFieldFamilia.setEditable(false);
            textFieldPreco.setText(String.valueOf(produtoLoja.getPreco()));
            checkBoxDisponibilidade.setSelected(produtoLoja.getDisponibilidade().equals(produtoLoja.getDisponibilidade().SIM));
            textFieldPreco.requestFocus();
        }

    }

    public void guardar() {

        if (pedido.equalsIgnoreCase("novoP")) {

            if (textFieldCodigoBarras.getText().isEmpty()) {
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

        }

        if (textFieldPreco.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduza p.f. o preço do produto!");
            textFieldPreco.requestFocus();
            return;
        }

        if (pedido.equalsIgnoreCase("novoP")) {
            //Criação do novo produto para adicionar ao repositorio de produtos da aplicação
            Produto produto = new Produto();
            produto.setCodigoBarras(textFieldCodigoBarras.getText());
            produto.setMarca(textFieldMarca.getText());
            produto.setModelo(textFieldModelo.getText());
            produto.setCarateristicas(textFieldCarateristicas.getText());
            produto.setFamilia(textFieldFamilia.getText());

            try {
                sistema.getRepositorioProdutos().adicionarProduto(produto);
            } catch (RepositorioProdutos.ProdutoJaExisteException ex) {
                JOptionPane.showMessageDialog(this, "Já existe um produto com esse código de barras");
                fechar();
                return;
            }

            //Criação do novo produto para a loja
            produtoLoja = new ProdutoLoja();
            produtoLoja.setProduto(produto);
            produtoLoja.setLoja(lojaLigada);
            produtoLoja.setDisponibilidade(checkBoxDisponibilidade.isSelected());

            try {
                produtoLoja.setPreco(Double.valueOf(textFieldPreco.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O campo preço só aceita valores numéricos");
                textFieldPreco.setText("");
                textFieldPreco.requestFocus();
                return;
            }

            try {
                lojaLigada.getRepositorioProdutos().adicionarProdutoLoja(produtoLoja);
            } catch (RepositorioProdutosLoja.ProdutoDuplicadoException ex) {
                JOptionPane.showMessageDialog(this, "Já existe um produto com esse código de barras");
                fechar();
                return;
            }

        } else if (pedido.equalsIgnoreCase("novoPL")) {

            //Definir os dados do novo produto para a loja
            produtoLoja.setLoja(lojaLigada);
            produtoLoja.setDisponibilidade(checkBoxDisponibilidade.isSelected());
            try {
                produtoLoja.setPreco(Double.valueOf(textFieldPreco.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O campo preço só aceita valores numéricos");
                textFieldPreco.setText("");
                textFieldPreco.requestFocus();
                return;
            }

            try {
                lojaLigada.getRepositorioProdutos().adicionarProdutoLoja(produtoLoja);
            } catch (RepositorioProdutosLoja.ProdutoDuplicadoException ex) {
                JOptionPane.showMessageDialog(this, "Já existe um produto com esse código de barras");
                fechar();
                return;
            }

        } else if (pedido.equalsIgnoreCase("editarP")) {
            //Definir os dados do novo produto para a loja
            produtoLoja.setDisponibilidade(checkBoxDisponibilidade.isSelected());

            try {
                produtoLoja.setPreco(Double.valueOf(textFieldPreco.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O campo preço só aceita valores numéricos");
                textFieldPreco.setText("");
                textFieldPreco.requestFocus();
                return;
            }

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
        textFieldPreco = new javax.swing.JTextField();
        labelPreco = new javax.swing.JLabel();
        labelDisponibilidade = new javax.swing.JLabel();
        checkBoxDisponibilidade = new javax.swing.JCheckBox();

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

        labelPreco.setText("Preço:");

        labelDisponibilidade.setText("Disponibilidade:");

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lableFamilia)
                                    .addComponent(lableCarateristicas)
                                    .addComponent(labelModelo)
                                    .addComponent(labelMarca)
                                    .addComponent(labelCodigoBarras)
                                    .addComponent(labelPreco)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelDisponibilidade)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldCodigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldCarateristicas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkBoxDisponibilidade))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPreco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDisponibilidade)
                    .addComponent(checkBoxDisponibilidade, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JCheckBox checkBoxDisponibilidade;
    private javax.swing.JLabel labelCodigoBarras;
    private javax.swing.JLabel labelDisponibilidade;
    private javax.swing.JLabel labelMarca;
    private javax.swing.JLabel labelModelo;
    private javax.swing.JLabel labelPreco;
    private javax.swing.JLabel lableCarateristicas;
    private javax.swing.JLabel lableFamilia;
    private javax.swing.JTextField textFieldCarateristicas;
    private javax.swing.JTextField textFieldCodigoBarras;
    private javax.swing.JTextField textFieldFamilia;
    private javax.swing.JTextField textFieldMarca;
    private javax.swing.JTextField textFieldModelo;
    private javax.swing.JTextField textFieldPreco;
    // End of variables declaration//GEN-END:variables
}

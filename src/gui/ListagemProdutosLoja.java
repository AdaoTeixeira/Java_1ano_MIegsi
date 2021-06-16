package gui;

import backend.Loja;
import backend.ProdutoLoja;
import backend.RepositorioProdutosLoja;
import backend.Sistema;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class ListagemProdutosLoja extends javax.swing.JPanel {
    //Variaveis de instancia
    private Sistema sistema;
    private JanelaPrincipal principal;
    private AbstractTableModel modeloTabela;
    private Loja lojaLigada;

    public ListagemProdutosLoja(Sistema sistema,JanelaPrincipal principal,Loja lojaLigada){
        initComponents();
        this.sistema = sistema;
        this.principal = principal;
        this.lojaLigada = lojaLigada;
        this.modeloTabela = criarTabelaModelo();
        tabListagemProdutos.setModel(modeloTabela);
        
    }

    private AbstractTableModel criarTabelaModelo() {
        String[] nomeColunas = {"Codigo Barras", "Marca","Modelo","Carateristicas","Familia","Preço","Disponibilidade"};

        return new AbstractTableModel() {     //Desenhar a tabela
            @Override
            public String getColumnName(int column) {
                return nomeColunas[column];
            }

            @Override
            public int getRowCount() {
                return lojaLigada.getListaProdutos().size();
            }

            @Override
            public int getColumnCount() {
                //Retorna o número de colunas que a tabela deverá ter
                return nomeColunas.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {

                switch (columnIndex) {
                    case 0:
                        return lojaLigada.getListaProdutos().get(rowIndex).getCodigoBarras();
                    case 1:
                        return lojaLigada.getListaProdutos().get(rowIndex).getProduto().getMarca();
                    case 2:
                        return lojaLigada.getListaProdutos().get(rowIndex).getProduto().getModelo();
                    case 3:
                        return lojaLigada.getListaProdutos().get(rowIndex).getProduto().getCarateristicas();
                    case 4:
                        return lojaLigada.getListaProdutos().get(rowIndex).getProduto().getFamilia();
                    case 5:
                        return lojaLigada.getListaProdutos().get(rowIndex).getPreco();
                    case 6:
                        return lojaLigada.getListaProdutos().get(rowIndex).getDisponibilidade().getEstado();
                    default:
                        return "";
                }
            }
        };

    }

    public void atualizar() {
        //Informa o modelo que foram efetuadas alteracoes, o modelo informa a tabela e os dados são redesenhados
        modeloTabela.fireTableDataChanged();
    }

    private void adicionar() {
        JanelaPesquisaPorCB janela = new JanelaPesquisaPorCB(sistema,lojaLigada,this);
        janela.setVisible(true);
    }
    
    private void editar() {
        int rowIndex = tabListagemProdutos.getSelectedRow();
        //Se nenhum registo selecionado, nao é possivel editar
        if (rowIndex == -1) return;
        
        String codigoBarras = (String) modeloTabela.getValueAt(rowIndex, 0);
        try{
        ProdutoLoja produtoLoja = lojaLigada.getRepositorioProdutos().getProdutoLojaByCodigoBarras(codigoBarras);
        JanelaDadosProdutoLoja janela = new JanelaDadosProdutoLoja(sistema, lojaLigada,"editarP", produtoLoja, this);
        janela.setVisible(true);
        }catch(RepositorioProdutosLoja.ProdutoNaoExisteException ex){
            JOptionPane.showMessageDialog(this, "Não existe um produto com esse código de barras");
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

        jScrollPane = new javax.swing.JScrollPane();
        tabListagemProdutos = new javax.swing.JTable();
        buttonAdicionar = new javax.swing.JButton();
        buttonEditar = new javax.swing.JButton();
        buttonVoltar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(790, 386));

        jScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        tabListagemProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabListagemProdutos.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(tabListagemProdutos);

        buttonAdicionar.setText("Adicionar");
        buttonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdicionarActionPerformed(evt);
            }
        });

        buttonEditar.setText("Editar");
        buttonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditarActionPerformed(evt);
            }
        });

        buttonVoltar.setText("Voltar");
        buttonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(buttonEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAdicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_buttonAdicionarActionPerformed

    private void buttonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditarActionPerformed
        editar();
    }//GEN-LAST:event_buttonEditarActionPerformed

    private void buttonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVoltarActionPerformed
        principal.voltarAmbientePrincipal();
    }//GEN-LAST:event_buttonVoltarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdicionar;
    private javax.swing.JButton buttonEditar;
    private javax.swing.JButton buttonVoltar;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable tabListagemProdutos;
    // End of variables declaration//GEN-END:variables
}

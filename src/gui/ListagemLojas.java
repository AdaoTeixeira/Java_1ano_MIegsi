package gui;

import backend.Loja;
import backend.Sistema;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class ListagemLojas extends javax.swing.JPanel {
    //Variaveis de instancia
    private Sistema sistema;
    private JanelaPrincipal principal;
    private ArrayList<Loja> listaLojas;
    private AbstractTableModel modeloTabela;
    private String tipo;
    private String[] nomeColunas;

    public ListagemLojas(Sistema sistema, JanelaPrincipal principal, String tipo) {
        initComponents();
        this.sistema = sistema;
        this.principal = principal;
        this.tipo = tipo;
        this.modeloTabela = criarTabelaModelo();
        tabListagemLojas.setModel(modeloTabela);

        if (tipo.equals("todas")) {
            buttonVoltar.setVisible(true);
            buttonAdicionar.setVisible(true);
            buttonEditarDados.setVisible(true);
            buttonAlterarEstado.setVisible(false);
            buttonValidarRegistos.setVisible(false);
            buttonValidarPedido.setVisible(false);
            this.listaLojas = sistema.getRepositorioLojas().listarTodos();

        } else if (tipo.equals("novas")) {
            buttonVoltar.setVisible(true);
            buttonAdicionar.setVisible(false);
            buttonEditarDados.setVisible(false);
            buttonAlterarEstado.setVisible(false);
            buttonValidarRegistos.setVisible(true);
            buttonValidarPedido.setVisible(false);
            this.listaLojas = sistema.getRepositorioLojas().listaLojasNovas();

        } else if (tipo.equals("ativas") | tipo.equals("suspensas")) {
            buttonVoltar.setVisible(true);
            buttonAdicionar.setVisible(false);
            buttonEditarDados.setVisible(false);
            buttonAlterarEstado.setVisible(true);
            buttonValidarRegistos.setVisible(false);
            buttonValidarPedido.setVisible(false);
            if (tipo.equals("ativas")) {
                this.listaLojas = sistema.getRepositorioLojas().listaLojasAtivas();
            } else {
                this.listaLojas = sistema.getRepositorioLojas().listaLojasSuspensas();
            }

        } else if (tipo.equals("pedidos cliques")) {
            buttonVoltar.setVisible(true);
            buttonAdicionar.setVisible(false);
            buttonEditarDados.setVisible(false);
            buttonAlterarEstado.setVisible(false);
            buttonValidarRegistos.setVisible(false);
            buttonValidarPedido.setVisible(true);
            this.listaLojas = sistema.getRepositorioLojas().listaNovosPedidosCliques();

        } else if (tipo.equals("poucos cliques")) {
            buttonVoltar.setVisible(true);
            buttonAdicionar.setVisible(false);
            buttonEditarDados.setVisible(false);
            buttonAlterarEstado.setVisible(false);
            buttonValidarRegistos.setVisible(false);
            buttonValidarPedido.setVisible(false);
            this.listaLojas = sistema.getRepositorioLojas().listaLojasPoucosCliques();

        } else if (tipo.equals("top 5")) {
            buttonVoltar.setVisible(true);
            buttonAdicionar.setVisible(false);
            buttonEditarDados.setVisible(false);
            buttonAlterarEstado.setVisible(false);
            buttonValidarRegistos.setVisible(false);
            buttonValidarPedido.setVisible(false);
            this.listaLojas = sistema.getRepositorioLojas().listaLojasMaisPopulares();
        }

    }

    //Cria uma tabela
    private AbstractTableModel criarTabelaModelo() {
        if (tipo.equals("pedidos cliques") | tipo.equals("novas")) {
            String[] nomeColunasPedidoCliques = {"Loja", "Estado subscrição", "Número cliques disponíveis", "Número de visitas", "Número de cliques pedidos"};
            nomeColunas = nomeColunasPedidoCliques;
        } else {
            String[] nomeColunasLojas = {"Loja", "Estado subscrição", "Número cliques disponíveis", "Número de visitas"};
            nomeColunas = nomeColunasLojas;
        }

        return new AbstractTableModel() {     //Desenhar a tabela
            @Override
            public String getColumnName(int column) {
                return nomeColunas[column];
            }

            //Retorna o número de linhas que a tabela deverá ter
            @Override
            public int getRowCount() {
                return listaLojas.size();
            }

            //Retorna o número de colunas que a tabela deverá ter
            @Override
            public int getColumnCount() {

                return nomeColunas.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {

                switch (columnIndex) {
                    case 0:
                        return listaLojas.get(rowIndex).getNome();
                    case 1:
                        return listaLojas.get(rowIndex).getSubscricao().getEstado();
                    case 2:
                        return listaLojas.get(rowIndex).getNumeroCliquesAtuais();
                    case 3:
                        return listaLojas.get(rowIndex).getNumeroCliquesUtilizados();
                    case 4:

                        return listaLojas.get(rowIndex).getPedidoCliques();
                    default:
                        return "";
                }
            }
        };

    }

    //Atualiza a minha tabela de é necessário redefinir a listaLojas pois estamos a usar a mesma classe para 6 tipos de listagens diferentes
    public void atualizar() {
        if (tipo.equals("todas")) {
            listaLojas = sistema.getRepositorioLojas().listarTodos();
        }
        
        if (tipo.equals("novas")){
            listaLojas = sistema.getRepositorioLojas().listaLojasNovas();
        }
        
        if (tipo.equals("ativas")){
            listaLojas = sistema.getRepositorioLojas().listaLojasAtivas();
        }
        
        if (tipo.equals("suspensas")){
            listaLojas = sistema.getRepositorioLojas().listaLojasSuspensas();
        }
        
        if (tipo.equals("pedidos cliques")){
            listaLojas = sistema.getRepositorioLojas().listaNovosPedidosCliques();
        }  
        modeloTabela.fireTableDataChanged();

    }

    //Altera o estado da loja selecionada
    public void alterarEstadoLoja() {
        if (tabListagemLojas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma Loja p.f.");
        } else {
            //Devolve o numero da linha selecionada
            int rowIndex = tabListagemLojas.getSelectedRow();
            Loja lojaSeleciona = listaLojas.get(rowIndex);

            //Desconta um clique na loja, do produto que estiver selecionado
            lojaSeleciona.alterarEstadoSubscricao();
            JOptionPane.showMessageDialog(this, "O estado da " + lojaSeleciona.getNome() + " foi alterado!");
        }
        atualizar();
    }

    //Valida todas as novas lojas
    public void validarTodosRegistos() {
        sistema.getRepositorioLojas().validarTodasLojasNovas();
        JOptionPane.showMessageDialog(this, "O registo de todas as novas lojas foram validados");
        atualizar();
    }

    //Adicionar uma loja
    public void adicionarLoja() {
        JanelaRegistoLoja janelaRegisto = new JanelaRegistoLoja(sistema, null, this);
        janelaRegisto.setVisible(true);
    }

    //Editar Dados da Loja
    public void editarDadosLoja() {
        if (tabListagemLojas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma Loja p.f.");
        } else {
            int rowIndex = tabListagemLojas.getSelectedRow();
            Loja l = listaLojas.get(rowIndex);
            JanelaRegistoLoja janelaDadosLoja = new JanelaRegistoLoja(sistema, l, this);
            janelaDadosLoja.setVisible(true);
        }
        atualizar();
    }

    //Validar os cliques pedidos por um loja
    public void validarPedidoCliques() {
        if (tabListagemLojas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma Loja p.f.");
        } else {
            //Devolve o numero da linha selecionada
            int rowIndex = tabListagemLojas.getSelectedRow();
            Loja lojaSeleciona = listaLojas.get(rowIndex);
            if (lojaSeleciona.getPedidoCliques() == 0) {
                JOptionPane.showMessageDialog(this, "A loja " + lojaSeleciona.getNome() + " não tem qualquer pedido!");
            } else {
                //Desconta um clique na loja, do produto que estiver selecionado
                lojaSeleciona.adicionarCliques();
                JOptionPane.showMessageDialog(this, "Foram adicionados os cliques a loja " + lojaSeleciona.getNome());
            }
        }
        atualizar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane = new javax.swing.JScrollPane();
        tabListagemLojas = new javax.swing.JTable();
        buttonVoltar = new javax.swing.JButton();
        buttonAlterarEstado = new javax.swing.JButton();
        buttonValidarRegistos = new javax.swing.JButton();
        buttonValidarPedido = new javax.swing.JButton();
        buttonAdicionar = new javax.swing.JButton();
        buttonEditarDados = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(790, 386));

        jScrollPane.setBackground(new java.awt.Color(204, 204, 204));

        tabListagemLojas.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.lightGray));
        tabListagemLojas.setModel(new javax.swing.table.DefaultTableModel(
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
        tabListagemLojas.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(tabListagemLojas);

        buttonVoltar.setText("Voltar");
        buttonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVoltarActionPerformed(evt);
            }
        });

        buttonAlterarEstado.setText("Alterar Estado");
        buttonAlterarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAlterarEstadoActionPerformed(evt);
            }
        });

        buttonValidarRegistos.setText("Validar Registos");
        buttonValidarRegistos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonValidarRegistosActionPerformed(evt);
            }
        });

        buttonValidarPedido.setText("Validar Pedido");
        buttonValidarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonValidarPedidoActionPerformed(evt);
            }
        });

        buttonAdicionar.setText("Adicionar");
        buttonAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAdicionarActionPerformed(evt);
            }
        });

        buttonEditarDados.setText("Editar Dados");
        buttonEditarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditarDadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(buttonAdicionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonEditarDados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAlterarEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonValidarPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonValidarRegistos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonVoltar)
                    .addComponent(buttonValidarRegistos)
                    .addComponent(buttonValidarPedido)
                    .addComponent(buttonAlterarEstado)
                    .addComponent(buttonAdicionar)
                    .addComponent(buttonEditarDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVoltarActionPerformed
        principal.voltarAmbientePrincipal();
    }//GEN-LAST:event_buttonVoltarActionPerformed

    private void buttonAlterarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAlterarEstadoActionPerformed
        alterarEstadoLoja();
    }//GEN-LAST:event_buttonAlterarEstadoActionPerformed

    private void buttonValidarRegistosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonValidarRegistosActionPerformed
        validarTodosRegistos();
    }//GEN-LAST:event_buttonValidarRegistosActionPerformed

    private void buttonValidarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonValidarPedidoActionPerformed
        validarPedidoCliques();
    }//GEN-LAST:event_buttonValidarPedidoActionPerformed

    private void buttonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAdicionarActionPerformed
        adicionarLoja();
    }//GEN-LAST:event_buttonAdicionarActionPerformed

    private void buttonEditarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditarDadosActionPerformed
        editarDadosLoja();
    }//GEN-LAST:event_buttonEditarDadosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdicionar;
    private javax.swing.JButton buttonAlterarEstado;
    private javax.swing.JButton buttonEditarDados;
    private javax.swing.JButton buttonValidarPedido;
    private javax.swing.JButton buttonValidarRegistos;
    private javax.swing.JButton buttonVoltar;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable tabListagemLojas;
    // End of variables declaration//GEN-END:variables
}

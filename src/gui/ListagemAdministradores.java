package gui;

import backend.Administrador;
import backend.RepositorioAdministradores;
import backend.Sistema;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class ListagemAdministradores extends javax.swing.JPanel {
    //Variaveis de instancia
    private Sistema sistema;
    private JanelaPrincipal principal;
    private AbstractTableModel modeloTabela;

    public ListagemAdministradores(Sistema sistema, JanelaPrincipal principal) {
        initComponents();
        this.sistema = sistema;
        this.principal = principal;
        this.modeloTabela = criarTabelaModelo();
        tabListagemAdministradores.setModel(modeloTabela);

    }

    private AbstractTableModel criarTabelaModelo() {
        String[] nomeColunas = {"Username", "Nome"};

        return new AbstractTableModel() {     //Desenhar a tabela
            @Override
            public String getColumnName(int column) {
                return nomeColunas[column];
            }

            @Override
            public int getRowCount() {
                //Retorna o número de linhas que a tabela deverá ter
                return sistema.getListaAdministradores().size();
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
                        return sistema.getListaAdministradores().get(rowIndex).getUsername();
                    case 1:
                        return sistema.getListaAdministradores().get(rowIndex).getNome();
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
        JanelaDadosUtilizador janela = new JanelaDadosUtilizador(sistema, null, this);
        janela.setVisible(true);

    }

    private void editar() {
        int rowIndex = tabListagemAdministradores.getSelectedRow();
        //Se nenhum registo selecionado, nao é possivel editar
        if (rowIndex == -1) {
            return;
        }

        String username = (String) modeloTabela.getValueAt(rowIndex, 0);
        try {
            Administrador admin = sistema.getRepositorioAdministradores().getAdministradorByUsername(username);
            JanelaDadosUtilizador janela = new JanelaDadosUtilizador(sistema, admin, this);
            janela.setVisible(true);
        } catch (RepositorioAdministradores.AdministradorNaoExisteException ex) {
            JOptionPane.showMessageDialog(this, "Erro do sistema");
            sistema.terminar();
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
        tabListagemAdministradores = new javax.swing.JTable();
        buttonAdicionar = new javax.swing.JButton();
        buttonEditar = new javax.swing.JButton();
        buttonVoltar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(790, 386));

        jScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        tabListagemAdministradores.setModel(new javax.swing.table.DefaultTableModel(
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
        tabListagemAdministradores.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(tabListagemAdministradores);

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
            .addComponent(jScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(buttonEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVoltarActionPerformed
        principal.voltarAmbientePrincipal();
    }//GEN-LAST:event_buttonVoltarActionPerformed

    private void buttonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditarActionPerformed
        editar();
    }//GEN-LAST:event_buttonEditarActionPerformed

    private void buttonAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAdicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_buttonAdicionarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdicionar;
    private javax.swing.JButton buttonEditar;
    private javax.swing.JButton buttonVoltar;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable tabListagemAdministradores;
    // End of variables declaration//GEN-END:variables
}

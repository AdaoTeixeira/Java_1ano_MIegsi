package gui;

import backend.Loja;
import backend.RepositorioLojas;
import backend.Sistema;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class JanelaRegistoLoja extends JDialog {

    private int cliques;
    private Sistema sistema;
    private Loja loja;
    private ListagemLojas listagem;

    public JanelaRegistoLoja(Sistema sistema, Loja loja, ListagemLojas listagem) {
        initComponents();
        this.sistema = sistema;
        this.listagem = listagem;
        this.loja = loja;

        //Configurações da janela
        this.setTitle("Registar");
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //Define a janela em função de ser umm novo registo ou não
        if (novoRegisto()) {
            verSelecaoDeCliques(true);
            textFieldUtilizador.requestFocus();
        } else {
            verSelecaoDeCliques(false);
            textFieldUtilizador.setText(loja.getUsername());
            textFieldUtilizador.setEditable(false);
            textFieldNome.setText(loja.getNome());
            passwordField.requestFocus();
        }

    }

    //Verifica se é um novo registo ou se é apenas uma edição de dados
    public boolean novoRegisto() {
        return loja == null;
    }

    //Torna visivel a parte de compra de cliques
    public void verSelecaoDeCliques(boolean estado) {
        labelPacotesCliques.setVisible(estado);
        checkBox100Cliques.setVisible(estado);
        checkBox200Cliques.setVisible(estado);
        checkBox300Cliques.setVisible(estado);
    }

    //Valida um novo registo 
    public void validar() throws RepositorioLojas.LojaJaExisteException {
        if (novoRegisto()) {
            if (textFieldUtilizador.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Intruduza p.f. o username!");
                textFieldUtilizador.requestFocus();

            } else if (sistema.getRepositorioLojas().existeLoja(textFieldUtilizador.getText().toLowerCase())) {
                JOptionPane.showMessageDialog(this, "Username ocupado!");
                textFieldUtilizador.setText("");
                textFieldUtilizador.requestFocus();
                return;
            }

            if (!checkBox100Cliques.isSelected() && !checkBox200Cliques.isSelected() && !checkBox300Cliques.isSelected()) {
                JOptionPane.showMessageDialog(this, "Selecione p.f. um pacote de cliques!");
                return;
            }

        }

        if (textFieldNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Intruduza p.f. o nome!");
            textFieldNome.requestFocus();
            return;
        } else if (sistema.getRepositorioLojas().existeLojaComONome(textFieldNome.getText()) != null && !novoRegisto()) {
            if (!sistema.getRepositorioLojas().existeLojaComONome(textFieldNome.getText()).getUsername().equalsIgnoreCase(loja.getUsername())) {
                JOptionPane.showMessageDialog(this, "Já existe uma loja registada com esse nome!");
                textFieldNome.requestFocus();
                return;
            }

        } else if (sistema.getRepositorioLojas().existeLojaComONome(textFieldNome.getText()) != null && novoRegisto()) {
            JOptionPane.showMessageDialog(this, "Já existe uma loja registada com esse nome!");
            textFieldNome.requestFocus();
            return;
        }

        if (passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Intruduza p.f. a sua password!");
            passwordField.requestFocus();
            return;
        }

        if (passwordFieldConfirmacao.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Intruduza p.f. novamente a sua password  no campo confirmação!");
            passwordFieldConfirmacao.requestFocus();
            return;
        }

        String password = new String(passwordField.getPassword());

        if (!password.equals(String.valueOf(passwordFieldConfirmacao.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Campo password difere do campo confirmação!");
            passwordFieldConfirmacao.setText("");
            passwordFieldConfirmacao.requestFocus();
            return;

        }

        if (novoRegisto()) {
            sistema.getRepositorioLojas().adicionarLoja(new Loja(textFieldUtilizador.getText().toLowerCase(), new String(passwordField.getPassword()), textFieldNome.getText(), cliques));
            JOptionPane.showMessageDialog(this, "O seu registo foi efectuado com sucesso!\n Obrigado!");
        } else {
            loja.setNome(textFieldNome.getText());
            loja.setPassword(String.valueOf(passwordField.getPassword()));
            JOptionPane.showMessageDialog(this, "Dados alterados!");
        }

        if (listagem != null) {
            listagem.atualizar();
        }

        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passwordField = new javax.swing.JPasswordField();
        labelPassword = new javax.swing.JLabel();
        buttonRegistar = new javax.swing.JButton();
        buttonTerminar = new javax.swing.JButton();
        textFieldUtilizador = new javax.swing.JTextField();
        labelUsername = new javax.swing.JLabel();
        textFieldNome = new javax.swing.JTextField();
        labelNome = new javax.swing.JLabel();
        checkBox100Cliques = new javax.swing.JCheckBox();
        labelPacotesCliques = new javax.swing.JLabel();
        checkBox200Cliques = new javax.swing.JCheckBox();
        checkBox300Cliques = new javax.swing.JCheckBox();
        labelConfirmacao = new javax.swing.JLabel();
        passwordFieldConfirmacao = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        labelPassword.setText("Password:");

        buttonRegistar.setText("Validar");
        buttonRegistar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRegistarActionPerformed(evt);
            }
        });

        buttonTerminar.setText("Terminar");
        buttonTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTerminarActionPerformed(evt);
            }
        });

        textFieldUtilizador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldUtilizadorActionPerformed(evt);
            }
        });

        labelUsername.setText("Username:");

        labelNome.setText("Nome:");

        checkBox100Cliques.setText("100 Cliques");
        checkBox100Cliques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox100CliquesActionPerformed(evt);
            }
        });

        labelPacotesCliques.setText("Selecione um Pacote Cliques:");

        checkBox200Cliques.setText("200 Cliques");
        checkBox200Cliques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox200CliquesActionPerformed(evt);
            }
        });

        checkBox300Cliques.setText("300 Cliques");
        checkBox300Cliques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBox300CliquesActionPerformed(evt);
            }
        });

        labelConfirmacao.setText("Confirmação:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBox100Cliques)
                                .addGap(18, 18, 18)
                                .addComponent(checkBox200Cliques)
                                .addGap(18, 18, 18)
                                .addComponent(checkBox300Cliques))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(textFieldNome)
                                            .addComponent(textFieldUtilizador, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(labelUsername)
                                                .addGap(0, 153, Short.MAX_VALUE)))
                                        .addGap(41, 41, 41))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labelNome)
                                            .addComponent(labelPassword))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonRegistar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonTerminar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(passwordFieldConfirmacao, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelPacotesCliques)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(176, 176, 176))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelConfirmacao)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonRegistar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldUtilizador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(buttonTerminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelPassword)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelConfirmacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordFieldConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelPacotesCliques)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(checkBox200Cliques)
                        .addComponent(checkBox300Cliques))
                    .addComponent(checkBox100Cliques))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRegistarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRegistarActionPerformed
        try {
            validar();
        } catch (RepositorioLojas.LojaJaExisteException ex) {
            JOptionPane.showMessageDialog(this, "Username ocupado!");
            textFieldUtilizador.setText("");
            textFieldUtilizador.requestFocus();
        }
    }//GEN-LAST:event_buttonRegistarActionPerformed

    private void buttonTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTerminarActionPerformed
        dispose();
    }//GEN-LAST:event_buttonTerminarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    private void checkBox100CliquesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox100CliquesActionPerformed
        if (checkBox200Cliques.isSelected() | checkBox300Cliques.isSelected()) {
            JOptionPane.showMessageDialog(this, "Apenas pode selecionar um pacote de cliques");
            checkBox100Cliques.setSelected(false);
            checkBox200Cliques.setSelected(false);
            checkBox300Cliques.setSelected(false);
        } else {
            cliques = 100;
        }
    }//GEN-LAST:event_checkBox100CliquesActionPerformed

    private void checkBox200CliquesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox200CliquesActionPerformed
        if (checkBox100Cliques.isSelected() | checkBox300Cliques.isSelected()) {
            JOptionPane.showMessageDialog(this, "Apenas pode selecionar um pacote de cliques");
            checkBox100Cliques.setSelected(false);
            checkBox200Cliques.setSelected(false);
            checkBox300Cliques.setSelected(false);
        } else {
            cliques = 200;
        }
    }//GEN-LAST:event_checkBox200CliquesActionPerformed

    private void checkBox300CliquesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBox300CliquesActionPerformed
        if (checkBox100Cliques.isSelected() | checkBox200Cliques.isSelected()) {
            JOptionPane.showMessageDialog(this, "Apenas pode selecionar um pacote de cliques");
            checkBox100Cliques.setSelected(false);
            checkBox200Cliques.setSelected(false);
            checkBox300Cliques.setSelected(false);
        } else {
            cliques = 300;
        }
    }//GEN-LAST:event_checkBox300CliquesActionPerformed

    private void textFieldUtilizadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldUtilizadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldUtilizadorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonRegistar;
    private javax.swing.JButton buttonTerminar;
    private javax.swing.JCheckBox checkBox100Cliques;
    private javax.swing.JCheckBox checkBox200Cliques;
    private javax.swing.JCheckBox checkBox300Cliques;
    private javax.swing.JLabel labelConfirmacao;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelPacotesCliques;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JPasswordField passwordFieldConfirmacao;
    private javax.swing.JTextField textFieldNome;
    private javax.swing.JTextField textFieldUtilizador;
    // End of variables declaration//GEN-END:variables
}

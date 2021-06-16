package gui;

import backend.Loja;
import backend.Sistema;
import bd.Serializacao;
import java.awt.CardLayout;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class JanelaPrincipal extends JFrame {

    private Sistema sistema;
    private Serializacao serializacao;
    private Loja lojaLigada;

    public JanelaPrincipal(Sistema sistema, Serializacao serializacao) {
        initComponents();
        setICon();
        this.sistema = sistema;
        this.serializacao = serializacao;
        this.setTitle("Kuanto Kusta");

        //Configurações da janela
        this.setResizable(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.menuUtilizador();

    }

    //Guarda as alterações no ficheiro
    private void guardarAlteracoes() {
        serializacao.guardar(sistema);
    }

    //Cria janela de login se não existir um utilizador ligado
    public void login() {
        if (sistema.getUtilizadorLigado() == null) {
            new JanelaLogin(sistema, this).setVisible(true);
        }
    }

    //Fecha a aplicação
    private void terminar() {
        if (JOptionPane.showConfirmDialog(this,
                "Deseja realmente terminar o programa?",
                "Terminar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            sistema.limparUtilizadorLigado();
            guardarAlteracoes();
            sistema.terminar();
        }
    }

    //Termina a sessão do utilizador ligado
    private void terminarSessao() {
        if (JOptionPane.showConfirmDialog(this,
                "Deseja realmente terminar a sua sessão?",
                "Terminar Sessão",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            sistema.limparUtilizadorLigado();
            guardarAlteracoes();
            menuUtilizador();
            voltarAmbientePrincipal();
        }
    }

    //Configurações do menu 
    public void menuUtilizador() {
        jMenuBarPrincipal.setVisible(true);
        jMenuGeral.setVisible(true);
        jMenuItemAutenticar.setVisible(true);
        jMenuItemRegistar.setVisible(true);
        jSeparator1.setVisible(true);
        jMenuItemTerminarSessao.setVisible(false);
        jSeparator12.setVisible(false);
        jMenuGestao.setVisible(false);
        jMenuListagem.setVisible(false);

    }

    public void menuAdmin() {
        jMenuBarPrincipal.setVisible(true);
        jMenuGeral.setVisible(true);
        jMenuItemAutenticar.setVisible(false);
        jMenuItemRegistar.setVisible(false);
        jSeparator1.setVisible(false);
        jMenuItemTerminarSessao.setVisible(true);
        jSeparator12.setVisible(true);
        jMenuGestao.setVisible(true);
        jMenuItemAdministradores.setVisible(true);
        jMenuItemProdutos.setVisible(true);
        jMenuGestaoLojas.setVisible(true);
        jMenuListagem.setVisible(true);
        jMenuItemOsSeusProdutos.setVisible(false);
        jMenuItemGerirCliques.setVisible(false);
        jSeparator8.setVisible(false);
        jSeparator10.setVisible(false);

    }

    public void menuLoja(Loja lojaLigada) {
        this.lojaLigada = lojaLigada;
        jMenuBarPrincipal.setVisible(true);
        jMenuGeral.setVisible(true);
        jMenuItemAutenticar.setVisible(false);
        jMenuItemRegistar.setVisible(false);
        jSeparator1.setVisible(false);
        jMenuItemTerminarSessao.setVisible(true);
        jSeparator12.setVisible(true);
        jMenuGestao.setVisible(true);
        jMenuItemAdministradores.setVisible(false);
        jMenuItemProdutos.setVisible(false);
        jMenuGestaoLojas.setVisible(false);
        jMenuItemOsSeusProdutos.setVisible(true);
        jMenuItemGerirCliques.setVisible(true);
        jMenuListagem.setVisible(false);
        jSeparator6.setVisible(false);
        jSeparator7.setVisible(false);
        jSeparator8.setVisible(false);
    }

    //Repõem o estado inicial da janela principals
    public void voltarAmbientePrincipal() {
        CardLayout cl = (CardLayout) jPanelPrincipal.getLayout();
        cl.addLayoutComponent(jPanelPesquisa, "pesquisa");
        jPanelResultadosPesquisa.removeAll();
        cl.show(jPanelPrincipal, "pesquisa");
    }

    //Adiciona um painel filho ao painel da janela Principal
    public void definirPainelAtual(JPanel panel, String tipoDeCard) {
        CardLayout cardLayout1 = (CardLayout) jPanelPrincipal.getLayout();
        jPanelPrincipal.add(panel, tipoDeCard);
        cardLayout1.show(jPanelPrincipal, tipoDeCard);
    }

    //listagem com todos os administradores registados na aplicação
    private void listarAdministradores() {
        ListagemAdministradores listaAdministradores = new ListagemAdministradores(sistema, this);
        definirPainelAtual(listaAdministradores, "listaAdmin");
    }

    //listagem com todos os produtos que correspondem á pesquisa feita
    public void procurarProduto() {
        if (textFieldPesquisa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira na barra de pesquisa a marca / modelo / carateristicas do produto que pretende procurar");
        } else {
            String procura = textFieldPesquisa.getText();
            ListagemResultados listaResultados = new ListagemResultados(sistema, this, sistema.getRepositorioLojas().listaProdutosLojasByCodigoBarras(sistema.getRepositorioProdutos().procuraProdutoComDetalhe(procura)), procura);
            CardLayout cardLayout2 = (CardLayout) jPanelResultadosPesquisa.getLayout();
            jPanelResultadosPesquisa.add(listaResultados, "resultados");
            cardLayout2.show(jPanelResultadosPesquisa, "resultados");

        }
    }

    //Listagem dos produtos presentes no repositorio
    public void listarProdutos() {
        ListagemProdutos listaProdutos = new ListagemProdutos(sistema, this);
        definirPainelAtual(listaProdutos, "produtos");

    }

    //listagem com todas as novas registadas na aplicaçao
    public void listarLojasSubscritas() {
        ListagemLojas lojasSubscritas = new ListagemLojas(sistema, this, "todas");
        definirPainelAtual(lojasSubscritas, "lojasSubuscritas");
    }

    //listagem com todas as novas acabadas de se registar
    public void listarNovasLojas() {
        ListagemLojas lojasNovas = new ListagemLojas(sistema, this, "novas");
        definirPainelAtual(lojasNovas, "lojasNovas");
    }

    //listagem com todas as Lojas Ativas
    public void listarLojasAtivas() {
        ListagemLojas lojasAtivas = new ListagemLojas(sistema, this, "ativas");
        definirPainelAtual(lojasAtivas, "lojasAtivas");
    }

    //listagem com todas as Lojas suspensas
    public void listarLojasSuspensas() {
        ListagemLojas lojasSuspensas = new ListagemLojas(sistema, this, "suspensas");
        definirPainelAtual(lojasSuspensas, "lojasSuspensas");
    }

    //cria uma janela de listagem com todos os novos pedidos de cliques por parte das lojas
    public void listarPedidosCliques() {
        ListagemLojas lojasComPedidosCliques = new ListagemLojas(sistema, this, "pedidos cliques");
        definirPainelAtual(lojasComPedidosCliques, "lojasComPedidosCliques");
    }

    //listagem com todas as lojas com poucos cliques
    public void listarLojasPoucosCliques() {
        ListagemLojas lojasComPoucosCliques = new ListagemLojas(sistema, this, "poucos cliques");
        definirPainelAtual(lojasComPoucosCliques, "lojasComPoucosCliques");
    }

    //listagem as 5 melhores lojas
    public void listarTop5Lojas() {
        ListagemLojas top5Lojas = new ListagemLojas(sistema, this, "top 5");
        definirPainelAtual(top5Lojas, "top5Lojas");
    }

    //listagem com os Produtos da loja ligada
    public void listarProdutosLojaLigada() {
        ListagemProdutosLoja listaProdutosLoja = new ListagemProdutosLoja(sistema, this, lojaLigada);
        definirPainelAtual(listaProdutosLoja, "listaProdutoLoja");
    }

    //Cria uma janela com Informação dos clqiues da loja e os pacotes disponiveis para compra
    public void infoCliques() {
        JanelaCompraCliques infoCliques = new JanelaCompraCliques(sistema, lojaLigada);
        infoCliques.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanelPrincipal = new javax.swing.JPanel();
        jPanelPesquisa = new javax.swing.JPanel();
        textFieldPesquisa = new javax.swing.JTextField();
        buttonPesquisar = new javax.swing.JButton();
        jPanelResultadosPesquisa = new javax.swing.JPanel();
        jMenuBarPrincipal = new javax.swing.JMenuBar();
        jMenuGeral = new javax.swing.JMenu();
        jMenuItemAutenticar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemRegistar = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenuItemTerminarSessao = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSair = new javax.swing.JMenuItem();
        jMenuGestao = new javax.swing.JMenu();
        jMenuItemAdministradores = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItemProdutos = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuGestaoLojas = new javax.swing.JMenu();
        jMenuItemTodas = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItemNovas = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItemAtivas = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSuspensas = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItemPedidosCliques = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItemOsSeusProdutos = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItemGerirCliques = new javax.swing.JMenuItem();
        jMenuListagem = new javax.swing.JMenu();
        jMenuItemLojasPoucosCliques = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        jMenuItemMelhoresLojas = new javax.swing.JMenuItem();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setSize(new java.awt.Dimension(120, 120));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPrincipal.setLayout(new java.awt.CardLayout());

        jPanelPesquisa.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPesquisa.setAlignmentX(50.0F);
        jPanelPesquisa.setAlignmentY(50.0F);

        textFieldPesquisa.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        textFieldPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldPesquisaActionPerformed(evt);
            }
        });

        buttonPesquisar.setBackground(new java.awt.Color(204, 204, 204));
        buttonPesquisar.setFont(new java.awt.Font("Franklin Gothic Medium Cond", 1, 24)); // NOI18N
        buttonPesquisar.setForeground(new java.awt.Color(255, 255, 255));
        buttonPesquisar.setText("Pesquisar");
        buttonPesquisar.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        buttonPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPesquisarActionPerformed(evt);
            }
        });

        jPanelResultadosPesquisa.setBackground(new java.awt.Color(255, 255, 255));
        jPanelResultadosPesquisa.setPreferredSize(new java.awt.Dimension(798, 386));
        jPanelResultadosPesquisa.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout jPanelPesquisaLayout = new javax.swing.GroupLayout(jPanelPesquisa);
        jPanelPesquisa.setLayout(jPanelPesquisaLayout);
        jPanelPesquisaLayout.setHorizontalGroup(
            jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPesquisaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textFieldPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanelResultadosPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelPesquisaLayout.setVerticalGroup(
            jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPesquisaLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jPanelResultadosPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelPrincipal.add(jPanelPesquisa, "card2");

        getContentPane().add(jPanelPrincipal, java.awt.BorderLayout.CENTER);

        jMenuBarPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBarPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jMenuGeral.setText("Geral");

        jMenuItemAutenticar.setText("Autenticar-se");
        jMenuItemAutenticar.setToolTipText("");
        jMenuItemAutenticar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAutenticarActionPerformed(evt);
            }
        });
        jMenuGeral.add(jMenuItemAutenticar);
        jMenuGeral.add(jSeparator1);

        jMenuItemRegistar.setText("Registar-se");
        jMenuItemRegistar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRegistarActionPerformed(evt);
            }
        });
        jMenuGeral.add(jMenuItemRegistar);
        jMenuGeral.add(jSeparator12);

        jMenuItemTerminarSessao.setText("Terminar Sessão");
        jMenuItemTerminarSessao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTerminarSessaoActionPerformed(evt);
            }
        });
        jMenuGeral.add(jMenuItemTerminarSessao);
        jMenuGeral.add(jSeparator2);

        jMenuItemSair.setText("Sair");
        jMenuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSairActionPerformed(evt);
            }
        });
        jMenuGeral.add(jMenuItemSair);

        jMenuBarPrincipal.add(jMenuGeral);

        jMenuGestao.setText("Gestão");
        jMenuGestao.setToolTipText("");

        jMenuItemAdministradores.setText("Administradores");
        jMenuItemAdministradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAdministradoresActionPerformed(evt);
            }
        });
        jMenuGestao.add(jMenuItemAdministradores);
        jMenuGestao.add(jSeparator6);

        jMenuItemProdutos.setText("Produtos");
        jMenuItemProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProdutosActionPerformed(evt);
            }
        });
        jMenuGestao.add(jMenuItemProdutos);
        jMenuGestao.add(jSeparator7);

        jMenuGestaoLojas.setText("Lojas");

        jMenuItemTodas.setText("Todas");
        jMenuItemTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTodasActionPerformed(evt);
            }
        });
        jMenuGestaoLojas.add(jMenuItemTodas);
        jMenuGestaoLojas.add(jSeparator3);

        jMenuItemNovas.setText("Novas");
        jMenuItemNovas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNovasActionPerformed(evt);
            }
        });
        jMenuGestaoLojas.add(jMenuItemNovas);
        jMenuGestaoLojas.add(jSeparator4);

        jMenuItemAtivas.setText("Ativas");
        jMenuItemAtivas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAtivasActionPerformed(evt);
            }
        });
        jMenuGestaoLojas.add(jMenuItemAtivas);
        jMenuGestaoLojas.add(jSeparator5);

        jMenuItemSuspensas.setText("Suspensas");
        jMenuItemSuspensas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSuspensasActionPerformed(evt);
            }
        });
        jMenuGestaoLojas.add(jMenuItemSuspensas);
        jMenuGestaoLojas.add(jSeparator9);

        jMenuItemPedidosCliques.setText("Pedidos Cliques");
        jMenuItemPedidosCliques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPedidosCliquesActionPerformed(evt);
            }
        });
        jMenuGestaoLojas.add(jMenuItemPedidosCliques);

        jMenuGestao.add(jMenuGestaoLojas);
        jMenuGestao.add(jSeparator8);

        jMenuItemOsSeusProdutos.setText("Os Seus Produtos");
        jMenuItemOsSeusProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOsSeusProdutosActionPerformed(evt);
            }
        });
        jMenuGestao.add(jMenuItemOsSeusProdutos);
        jMenuGestao.add(jSeparator10);

        jMenuItemGerirCliques.setText("Gerir Cliques");
        jMenuItemGerirCliques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGerirCliquesActionPerformed(evt);
            }
        });
        jMenuGestao.add(jMenuItemGerirCliques);

        jMenuBarPrincipal.add(jMenuGestao);

        jMenuListagem.setText("Listagem");
        jMenuListagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuListagemActionPerformed(evt);
            }
        });

        jMenuItemLojasPoucosCliques.setText("Lojas Com Poucos Cliques");
        jMenuItemLojasPoucosCliques.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLojasPoucosCliquesActionPerformed(evt);
            }
        });
        jMenuListagem.add(jMenuItemLojasPoucosCliques);
        jMenuListagem.add(jSeparator11);

        jMenuItemMelhoresLojas.setText("TOP 5 Lojas");
        jMenuItemMelhoresLojas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMelhoresLojasActionPerformed(evt);
            }
        });
        jMenuListagem.add(jMenuItemMelhoresLojas);

        jMenuBarPrincipal.add(jMenuListagem);

        setJMenuBar(jMenuBarPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemRegistarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRegistarActionPerformed
        new JanelaRegistoLoja(sistema, null, null).setVisible(true);
    }//GEN-LAST:event_jMenuItemRegistarActionPerformed

    private void jMenuItemSuspensasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSuspensasActionPerformed
        listarLojasSuspensas();
    }//GEN-LAST:event_jMenuItemSuspensasActionPerformed

    private void jMenuItemTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTodasActionPerformed
        listarLojasSubscritas();
    }//GEN-LAST:event_jMenuItemTodasActionPerformed

    private void jMenuItemPedidosCliquesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPedidosCliquesActionPerformed
        listarPedidosCliques();
    }//GEN-LAST:event_jMenuItemPedidosCliquesActionPerformed

    private void jMenuItemAutenticarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAutenticarActionPerformed
        login();
    }//GEN-LAST:event_jMenuItemAutenticarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        terminar();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItemSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSairActionPerformed
        terminar();
    }//GEN-LAST:event_jMenuItemSairActionPerformed

    private void jMenuItemAdministradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAdministradoresActionPerformed
        listarAdministradores();
    }//GEN-LAST:event_jMenuItemAdministradoresActionPerformed

    private void jMenuItemProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProdutosActionPerformed
        listarProdutos();
    }//GEN-LAST:event_jMenuItemProdutosActionPerformed

    private void jMenuItemNovasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNovasActionPerformed
        listarNovasLojas();
    }//GEN-LAST:event_jMenuItemNovasActionPerformed

    private void jMenuItemAtivasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAtivasActionPerformed
        listarLojasAtivas();
    }//GEN-LAST:event_jMenuItemAtivasActionPerformed

    private void jMenuListagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuListagemActionPerformed

    }//GEN-LAST:event_jMenuListagemActionPerformed

    private void jMenuItemMelhoresLojasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMelhoresLojasActionPerformed
        listarTop5Lojas();
    }//GEN-LAST:event_jMenuItemMelhoresLojasActionPerformed

    private void jMenuItemLojasPoucosCliquesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLojasPoucosCliquesActionPerformed
        listarLojasPoucosCliques();
    }//GEN-LAST:event_jMenuItemLojasPoucosCliquesActionPerformed

    private void jMenuItemOsSeusProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOsSeusProdutosActionPerformed
        listarProdutosLojaLigada();
    }//GEN-LAST:event_jMenuItemOsSeusProdutosActionPerformed

    private void jMenuItemTerminarSessaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTerminarSessaoActionPerformed
        terminarSessao();
    }//GEN-LAST:event_jMenuItemTerminarSessaoActionPerformed

    private void jMenuItemGerirCliquesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGerirCliquesActionPerformed
        infoCliques();
    }//GEN-LAST:event_jMenuItemGerirCliquesActionPerformed

    private void buttonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPesquisarActionPerformed
        procurarProduto();
    }//GEN-LAST:event_buttonPesquisarActionPerformed

    private void textFieldPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldPesquisaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPesquisar;
    private javax.swing.JMenuBar jMenuBarPrincipal;
    private javax.swing.JMenu jMenuGeral;
    private javax.swing.JMenu jMenuGestao;
    private javax.swing.JMenu jMenuGestaoLojas;
    private javax.swing.JMenuItem jMenuItemAdministradores;
    private javax.swing.JMenuItem jMenuItemAtivas;
    private javax.swing.JMenuItem jMenuItemAutenticar;
    private javax.swing.JMenuItem jMenuItemGerirCliques;
    private javax.swing.JMenuItem jMenuItemLojasPoucosCliques;
    private javax.swing.JMenuItem jMenuItemMelhoresLojas;
    private javax.swing.JMenuItem jMenuItemNovas;
    private javax.swing.JMenuItem jMenuItemOsSeusProdutos;
    private javax.swing.JMenuItem jMenuItemPedidosCliques;
    private javax.swing.JMenuItem jMenuItemProdutos;
    private javax.swing.JMenuItem jMenuItemRegistar;
    private javax.swing.JMenuItem jMenuItemSair;
    private javax.swing.JMenuItem jMenuItemSuspensas;
    private javax.swing.JMenuItem jMenuItemTerminarSessao;
    private javax.swing.JMenuItem jMenuItemTodas;
    private javax.swing.JMenu jMenuListagem;
    private javax.swing.JPanel jPanelPesquisa;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JPanel jPanelResultadosPesquisa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField textFieldPesquisa;
    // End of variables declaration//GEN-END:variables

    private void setICon() {

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("dog.png")));
    }

}

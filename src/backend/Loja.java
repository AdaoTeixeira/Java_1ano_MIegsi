package backend;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class Loja extends Utilizador implements Comparable<Loja>, Serializable {

    //Variaveis de instância
    private Subscricao subscricao;
    private int numeroCliquesAtuais;
    private int numeroCliquesUtilizados;
    private int pedidoCliques;
    private RepositorioProdutosLoja repositorioProdutos;

    //Construtores
    public Loja() {

    }

    public Loja(String username, String password, String nome, int cliques) {
        super(username, password, nome);
        numeroCliquesAtuais = 0;
        numeroCliquesUtilizados = 0;
        subscricao = Subscricao.PENDENTE;
        repositorioProdutos = new RepositorioProdutosLoja();
        pedidoCliques = cliques;
    }

    //Seletores
    public int getNumeroCliquesAtuais() {
        return numeroCliquesAtuais;
    }

    public int getNumeroCliquesUtilizados() {
        return numeroCliquesUtilizados;
    }

    public Subscricao getSubscricao() {
        return subscricao;
    }

    public int getPedidoCliques() {
        return pedidoCliques;
    }

    public RepositorioProdutosLoja getRepositorioProdutos() {
        return repositorioProdutos;
    }

    //Modificadores
    public void setSubscricao(Subscricao estado) {
        this.subscricao = estado;
    }

    public void setPedidoCliques(int pedidoCliques) {
        this.pedidoCliques = pedidoCliques;
    }

    //Métodos
    //Altera o estado de subscrição da loja
    public void alterarEstadoSubscricao() {
        if (subscricao.equals(Subscricao.ATIVA)) {
            subscricao = Subscricao.SUSPENSA;
        } else {
            subscricao = Subscricao.ATIVA;
        }
    }

    //Adiciona o valor de pedido de cliques à loja
    public void adicionarCliques() {
        this.numeroCliquesAtuais += pedidoCliques;
        pedidoCliques = 0;
    }

    //Consome um cliques da loja
    public void cliqueUtilizado() {
        numeroCliquesUtilizados++;
        if (numeroCliquesAtuais == 1) {
            numeroCliquesAtuais--;
            subscricao = Subscricao.SUSPENSA;
        }
        numeroCliquesAtuais--;
    }

    //Visualizar total de cliques subscritos
    public int totalCliques() {
        return numeroCliquesAtuais + numeroCliquesUtilizados;
    }

    //Lista com todos os produtos da loja
    public ArrayList<ProdutoLoja> getListaProdutos() {
        return repositorioProdutos.listarTodos();
    }

    //Compara numero de cliques utilizados, entre lojas
    @Override
    public int compareTo(Loja l) {
        if (this.numeroCliquesUtilizados < l.numeroCliquesUtilizados) {
            return 1;
        }
        if (this.numeroCliquesUtilizados > l.numeroCliquesUtilizados) {
            return -1;
        }
        return 0;
    }

}

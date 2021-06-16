package backend;

import java.io.Serializable;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class ProdutoLoja implements Comparable<ProdutoLoja>, Serializable {

    //Variaveis de instância
    private double preco;
    private Disponibilidade disponibilidade;
    private Produto produto;
    private Loja loja;

    //Construtor
    public ProdutoLoja() {

    }

    public ProdutoLoja(Produto produto, Loja loja, double preco, Disponibilidade disponibilidade) {
        this.produto = produto;
        this.loja = loja;
        this.preco = preco;
        this.disponibilidade = disponibilidade;
    }

    //Seletores
    public double getPreco() {
        return preco;
    }

    public Disponibilidade getDisponibilidade() {
        return disponibilidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public String getCodigoBarras() {
        return produto.getCodigoBarras();
    }

    public Loja getLoja() {
        return loja;
    }

    public String getInfoProduto() {
        return produto.getMarca() + " " + produto.getModelo() + " " + produto.getCarateristicas();
    }

    //Modificadores
    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setDisponibilidade(boolean estadoDisponibilidade) {
        if (estadoDisponibilidade) {
            this.disponibilidade = disponibilidade.SIM;
        } else {
            this.disponibilidade = disponibilidade.NAO;
        }
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    //Compara precos, entre Produtos de loja
    @Override
    public int compareTo(ProdutoLoja pl) {
        if (this.preco < pl.preco) {
            return -1;
        }
        if (this.preco > pl.preco) {
            return 1;
        }
        return 0;
    }

}

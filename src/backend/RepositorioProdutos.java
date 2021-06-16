package backend;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class RepositorioProdutos implements Serializable {

    //Variáveis de Instância
    private Map<String, Produto> listaProdutos;

    //Construtor
    public RepositorioProdutos() {
        listaProdutos = new HashMap<>();
    }

    //Metodos
    //Adicionar um produto
    public void adicionarProduto(Produto produto) throws ProdutoJaExisteException {
        if (produto == null) {
            throw new NullPointerException("Produto não pode ser um campo com um vaor nulo.");
        } else if (!listaProdutos.containsKey(produto.getCodigoBarras())) {
            listaProdutos.put(produto.getCodigoBarras(), produto);
        } else {
            throw new ProdutoJaExisteException("Esse produto já existe.");
        }
    }

    //Verificar se existe um produto
    public boolean existeProduto(String codigoBarras) {
        return listaProdutos.containsKey(codigoBarras);
    }

    //Fazer uma busca de um prodtuo pelo seu codigo de barras
    public Produto getProdutoByCodigoBarras(String codigoBarras) throws ProdutoNaoExisteException {
        if (listaProdutos.containsKey(codigoBarras)) {
            return listaProdutos.get(codigoBarras);
        } else {
            throw new ProdutoNaoExisteException("Não existe nenhuma produto com o código de barras: " + codigoBarras + ".");
        }
    }

    //Procura um produto por detalhe
    public ArrayList<String> procuraProdutoComDetalhe(String detalhe) {
        ArrayList<Produto> produtos = new ArrayList<>(listaProdutos.values());
        ArrayList<String> produtosComDetalhe = new ArrayList<>();
        for (Produto p : produtos) {
            String dadosProduto = (p.getCodigoBarras() + " " + p.getMarca() + " " + p.getModelo() + " " + p.getCarateristicas()).toLowerCase();
            if (dadosProduto.contains(detalhe.toLowerCase())) {
                produtosComDetalhe.add(p.getCodigoBarras());
            }
        }
        return produtosComDetalhe;
    }

    //Lista com todos os produtos
    public ArrayList<Produto> listarTodos() {
        return new ArrayList<>(listaProdutos.values());
    }

    //Subclass da classe Exception
    //Produto Duplicado
    public class ProdutoJaExisteException extends Exception {

        public ProdutoJaExisteException() {
        }

        public ProdutoJaExisteException(String message) {
            super(message);
        }
    }

    //Produto Não Existe
    public class ProdutoNaoExisteException extends Exception {

        public ProdutoNaoExisteException() {
        }

        public ProdutoNaoExisteException(String message) {
            super(message);
        }
    }

}

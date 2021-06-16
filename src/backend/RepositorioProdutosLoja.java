package backend;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class RepositorioProdutosLoja implements Serializable {

    //Variáveis de Instância
    private HashMap<String, ProdutoLoja> listaProdutosLoja;

    //Construtor
    public RepositorioProdutosLoja() {
        listaProdutosLoja = new HashMap<>();
    }

    //Metodos
    //Adicionar um produto
    public void adicionarProdutoLoja(ProdutoLoja produtoLoja) throws ProdutoDuplicadoException {
        if (produtoLoja == null) {
            throw new NullPointerException("Produto não pode ser um campo com um valor nulo");
        } else if (!listaProdutosLoja.containsKey(produtoLoja.getCodigoBarras())) {
            listaProdutosLoja.put(produtoLoja.getCodigoBarras(), produtoLoja);
        } else {
            throw new ProdutoDuplicadoException("Esse produto já existe");
        }
    }

    //Verificar se existe um produto
    public boolean existeProdutoLoja(String codigoBarras) {
        return listaProdutosLoja.containsKey(codigoBarras);
    }

    //Fazer uma busca de um prodtuo pelo seu codigo de barras
    public ProdutoLoja getProdutoLojaByCodigoBarras(String codigoBarras) throws ProdutoNaoExisteException {
        if (listaProdutosLoja.containsKey(codigoBarras)) {
            return listaProdutosLoja.get(codigoBarras);
        } else {
            throw new ProdutoNaoExisteException("Não existe nenhum produto com o código de barras: " + codigoBarras + ".");
        }
    }

    //Lista com todos os produtos da loja
    public ArrayList<ProdutoLoja> listarTodos() {
        return new ArrayList<>(listaProdutosLoja.values());
    }

    //Subclasses para as exceções
    //Produto Duplicado
    public class ProdutoDuplicadoException extends Exception {

        public ProdutoDuplicadoException() {
        }

        public ProdutoDuplicadoException(String message) {
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

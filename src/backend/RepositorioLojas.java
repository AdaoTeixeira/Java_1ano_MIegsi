package backend;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
import java.io.Serializable;
import java.util.*;

public class RepositorioLojas implements Serializable {

    //Variável de Instância
    private HashMap<String, Loja> listaLojas;

    //Construtor
    public RepositorioLojas() {
        this.listaLojas = new HashMap<>();
    }

    //Adicionar uma Loja
    public void adicionarLoja(Loja loja) throws LojaJaExisteException {
        if (loja == null) {
            throw new IllegalArgumentException("Loja nao pode ser um valor nulo.");
        } else if (!listaLojas.containsKey(loja.getUsername())) {
            listaLojas.put(loja.getUsername(), loja);
        } else {
            throw new LojaJaExisteException("A loja que tentou adicionar já existe.");
        }
    }

    //Verifica se existe uma determinada loja
    public boolean existeLoja(String username) {
        return listaLojas.containsKey(username);
    }

    //Verifica se já existe uma loja com um determinado nome
    public Loja existeLojaComONome(String nome) {
        Loja existe = null;
        for (Loja l : listarTodos()) {
            if (existe == null) {
                if (l.getNome().equalsIgnoreCase(nome)) {
                    existe = l;
                }
            }
        }
        return existe;
    }

    //Devolve uma loja através do seu username
    public Loja getLojaByUsername(String username) throws LojaNaoExisteException {
        if (existeLoja(username)) {
            return listaLojas.get(username);
        } else {
            throw new LojaNaoExisteException("Não existe nenhuma Loja com o username: " + username + ".");
        }
    }

    //Validar o registo das lojas novas (Estado Pendente)
    public void validarTodasLojasNovas() {
        for (Loja l : listaLojas.values()) {
            if (l.getSubscricao().equals(Subscricao.PENDENTE)) {
                l.adicionarCliques();
                l.setPedidoCliques(0);
                l.setSubscricao(Subscricao.ATIVA);
                listaLojas.put(l.getUsername(), l);
            }
        }
    }

    //Listagem de lojas
    //Lista com todas as lojas
    public ArrayList<Loja> listarTodos() {
        return new ArrayList<>(listaLojas.values());
    }

    //Lista todas as novas lojas
    public ArrayList<Loja> listaLojasNovas() {
        ArrayList<Loja> lojasNovas = new ArrayList<>();
        for (Loja l : listaLojas.values()) {
            if (l.getSubscricao().equals(Subscricao.PENDENTE)) {
                lojasNovas.add(l);
            }
        }
        return lojasNovas;
    }

    //Lista todas as lojas com subsrição ativa
    public ArrayList<Loja> listaLojasAtivas() {
        ArrayList<Loja> lojasAtivas = new ArrayList<>();
        for (Loja l : listaLojas.values()) {
            if (l.getSubscricao().equals(Subscricao.ATIVA)) {
                lojasAtivas.add(l);
            }
        }
        return lojasAtivas;
    }

    //Lista com todas as lojas com subscrição suspensa
    public ArrayList<Loja> listaLojasSuspensas() {
        ArrayList<Loja> lojasSuspensas = new ArrayList<>();
        for (Loja l : listaLojas.values()) {
            if (l.getSubscricao().equals(Subscricao.SUSPENSA)) {
                lojasSuspensas.add(l);
            }
        }
        return lojasSuspensas;
    }

    //Lista com todas as lojas com menos de 10 cliques disponiveis
    public ArrayList<Loja> listaLojasPoucosCliques() {
        ArrayList<Loja> lojasPoucosCliques = new ArrayList<>();
        for (Loja l : listaLojas.values()) {
            if (l.getNumeroCliquesAtuais() < 10 && l.getSubscricao().equals(Subscricao.ATIVA)) {
                lojasPoucosCliques.add(l);
            }
        }
        return lojasPoucosCliques;
    }

    //Lista com todas as lojas com pedidos de cliques
    public ArrayList<Loja> listaNovosPedidosCliques() {
        ArrayList<Loja> lojasPedidosCliques = new ArrayList<>();
        for (Loja l : listaLojas.values()) {
            if (l.getPedidoCliques() > 0) {
                lojasPedidosCliques.add(l);
            }
        }
        return lojasPedidosCliques;
    }

    //Lista com as 5 lojas mais populares
    public ArrayList<Loja> listaLojasMaisPopulares() {
        ArrayList<Loja> lojasMaisPopulares = new ArrayList<>(5);
        ArrayList<Loja> lojas = new ArrayList<>(listaLojas.values());
        int tamanho = lojas.size();
        int numeroDoTop = 5;
        Collections.sort(lojas);
        if (tamanho < 5) {
            numeroDoTop = tamanho;
        }
        for (int i = 0; i < numeroDoTop; i++) {
            if (!lojas.isEmpty()) {
                lojasMaisPopulares.add(lojas.get(i));
            }
        }
        return lojasMaisPopulares;
    }

    //Procura uma lista de Codigo de barras no repositorio de produtos de uma loja
    public ArrayList<ProdutoLoja> listaProdutosLojasByCodigoBarras(ArrayList<String> codigoBarrasProdutos) {
        ArrayList<ProdutoLoja> produtosLojas = new ArrayList<>();
        for (Loja l : listaLojasAtivas()) {
            for (String s : codigoBarrasProdutos) {
                if (l.getRepositorioProdutos().existeProdutoLoja(s)) {
                    try {
                        produtosLojas.add(l.getRepositorioProdutos().getProdutoLojaByCodigoBarras(s));
                    } catch (RepositorioProdutosLoja.ProdutoNaoExisteException ex) {
                    }
                }
            }
        }
        Collections.sort(produtosLojas);
        return produtosLojas;
    }

    //Subclass da classe Exception
    //Execeção para quando já existe loja
    public class LojaJaExisteException extends Exception {

        public LojaJaExisteException() {
        }

        public LojaJaExisteException(String message) {
            super(message);
        }
    }

    //Execeção para quando não existe loja
    public class LojaNaoExisteException extends Exception {

        public LojaNaoExisteException() {
        }

        public LojaNaoExisteException(String message) {
            super(message);
        }
    }

}

package backend;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class Sistema implements Serializable {
   //Variaveis de instância
    private final RepositorioAdministradores repositorioAdministradores;
    private final RepositorioLojas repositorioLojas;
    private final RepositorioProdutos repositorioProdutos;
    private final List<RegistoAcesso> listaEntradas;
    private Utilizador utilizadorLigado;

    public Sistema() {
        repositorioAdministradores = new RepositorioAdministradores();
        listaEntradas = new ArrayList<>();
        repositorioLojas = new RepositorioLojas();
        repositorioProdutos = new RepositorioProdutos();
        utilizadorLigado = null;
    }

    //Seletores
    public RepositorioAdministradores getRepositorioAdministradores() {
        return repositorioAdministradores;
    }

    public RepositorioLojas getRepositorioLojas() {
        return repositorioLojas;
    }

    public RepositorioProdutos getRepositorioProdutos() {
        return repositorioProdutos;
    }

    public List<RegistoAcesso> getListaEntradas() {
        return listaEntradas;
    }

    public Utilizador getUtilizadorLigado() {
        return utilizadorLigado;
    }

    public void limparUtilizadorLigado() {
        utilizadorLigado = null;
    }

    //Modificadores
    //Autenticar o utilizador
    public boolean autenticarUtilizador(String username, String password) {
        if (repositorioAdministradores.existeAdministrador(username)) {
            try {
                Administrador a = repositorioAdministradores.getAdministradorByUsername(username);
                if (a.getPassword().equals(password)) {
                    utilizadorLigado = a;
                    listaEntradas.add(new RegistoAcesso(a, LocalDateTime.now()));
                    return true;
                }
            } catch (RepositorioAdministradores.AdministradorNaoExisteException e) {
            }
        }

        if (repositorioLojas.existeLoja(username)) {
            try {
                Loja l = repositorioLojas.getLojaByUsername(username);
                if (l.getPassword().equals(password)) {
                    utilizadorLigado = l;
                    listaEntradas.add(new RegistoAcesso(l, LocalDateTime.now()));
                    return true;
                }
            } catch (RepositorioLojas.LojaNaoExisteException e) {
            }
        }

        return false;
    }

    public void inicializar() throws RepositorioAdministradores.AdministradorJaExisteException {
        repositorioAdministradores.adicionarAdministrador(new Administrador("admin", "admin", "Aministrador"));
    }

    //Metodos de interação com Lojas
    public void validarRegistosLojas() {
        repositorioLojas.validarTodasLojasNovas();
    }

    public ArrayList<Loja> getListaLojas() {
        return repositorioLojas.listarTodos();
    }

    public ArrayList<Loja> getListaNovasLojas() {
        return repositorioLojas.listaLojasNovas();
    }

    public ArrayList<Loja> getListaLojasAtivas() {
        return repositorioLojas.listaLojasAtivas();
    }

    public ArrayList<Loja> getListaLojasSuspensas() {
        return repositorioLojas.listaLojasSuspensas();
    }

    public ArrayList<Loja> getListaLojasPoucosCliques() {
        return repositorioLojas.listaLojasPoucosCliques();
    }

    public ArrayList<Loja> getListaLojasMaisPopulares() {
        return repositorioLojas.listaLojasMaisPopulares();
    }

    //Metodos de interação com Produtos
    public ArrayList<ProdutoLoja> getlistaProdutosEncontradosByDetalhe(String detalhe) {
        return repositorioLojas.listaProdutosLojasByCodigoBarras(repositorioProdutos.procuraProdutoComDetalhe(detalhe));
    }

    public int getNumeroProdutosEncontradosByDetalhe(String detalhe) {
        return repositorioLojas.listaProdutosLojasByCodigoBarras(repositorioProdutos.procuraProdutoComDetalhe(detalhe)).size();
    }

    public double getPrecoMinProdutosEncontradosByDetalhe(String detalhe) {
        return Collections.min(repositorioLojas.listaProdutosLojasByCodigoBarras(repositorioProdutos.procuraProdutoComDetalhe(detalhe))).getPreco();
    }

    public double getPrecoMaxProdutosEncontradosByDetalhe(String detalhe) {
        return Collections.max(repositorioLojas.listaProdutosLojasByCodigoBarras(repositorioProdutos.procuraProdutoComDetalhe(detalhe))).getPreco();
    }

    //Metodos de interação para listar todos os dados de um tipo
    public ArrayList<Administrador> getListaAdministradores() {
        return repositorioAdministradores.listarTodos();
    }
    
    //Lista com todos os produtos
    public ArrayList<Produto> getListaProdutos() {
        return repositorioProdutos.listarTodos();
    }

    public void terminar() {
        System.exit(0);
    }

}

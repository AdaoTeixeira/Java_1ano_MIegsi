package backend;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class RepositorioAdministradores implements Serializable {

    //Variável de Instância
    private HashMap<String, Administrador> listaAdministradores;

    //Construtor
    public RepositorioAdministradores() {
        listaAdministradores = new HashMap<>();
    }

    //Adicionar um adiministrador
    public void adicionarAdministrador(Administrador a) throws AdministradorJaExisteException {
        if (a == null) {
            throw new IllegalArgumentException("Administrador não pode ser um valor nulo.");
        } else if (!listaAdministradores.containsKey(a.getUsername())) {
            listaAdministradores.put(a.getUsername(), a);
        } else {
            throw new AdministradorJaExisteException("O username: " + a.getUsername() + " já está ocupado.");
        }
    }

    //Verificar se existe um determinado Administrador
    public boolean existeAdministrador(String username) {
        return listaAdministradores.containsKey(username);
    }

    //Devolve um administrador através do seu username
    public Administrador getAdministradorByUsername(String username) throws AdministradorNaoExisteException {
        if (listaAdministradores.containsKey(username)) {
            return listaAdministradores.get(username);
        } else {
            throw new AdministradorNaoExisteException("Não existe nenhum Administrador com o username: " + username + ".");
        }
    }

    //Array com todos os administradores
    public ArrayList<Administrador> listarTodos() {
        return new ArrayList<>(listaAdministradores.values());
    }

    //Subclass da classe Exception
    //Execeção caso administrador já exista
    public class AdministradorJaExisteException extends Exception {

        public AdministradorJaExisteException() {
        }

        public AdministradorJaExisteException(String message) {
            super(message);
        }
    }

    //Execeção caso administrador não exista
    public class AdministradorNaoExisteException extends Exception {

        public AdministradorNaoExisteException() {
        }

        public AdministradorNaoExisteException(String message) {
            super(message);
        }
    }

}

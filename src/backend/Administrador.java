package backend;

import java.io.Serializable;
/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class Administrador extends Utilizador implements Serializable {

    //Construtores
    public Administrador() {
    }

    public Administrador(String username, String password, String nome) {
        super(username, password, nome);
    }
}

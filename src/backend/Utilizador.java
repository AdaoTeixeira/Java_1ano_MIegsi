package backend;

import java.io.Serializable;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class Utilizador implements Serializable {

    //Variaveis de Instância
    private String username;
    private String password;
    private String nome;

    //Construtores
    public Utilizador() {
    }

    public Utilizador(String username, String password, String nome) {
        this.username = username;
        this.password = password;
        this.nome = nome;
    }

    //Seletores
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    //Modificadores
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

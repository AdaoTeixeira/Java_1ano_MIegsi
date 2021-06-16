/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public enum Disponibilidade {
    SIM("Sim"),
    NAO("Não");

    private String estado;

    Disponibilidade(String estado) {
        this.estado = estado;
    }

    //Devolve um string com estado da disponibilidade
    public String getEstado() {
        return estado;
    }

}

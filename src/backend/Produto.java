package backend;

import java.io.Serializable;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public class Produto implements Serializable {

    //variaveis de instância
    private String codigoBarras;
    private String modelo;
    private String marca;
    private String carateristicas;
    private String familia;

    //construtores
    public Produto() {
    }

    public Produto(String codigoBarras, String marca, String modelo, String carateristicas, String familia) {
        this.codigoBarras = codigoBarras;
        this.modelo = modelo;
        this.marca = marca;
        this.carateristicas = carateristicas;
        this.familia = familia;
    }

    //Seletores
    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCarateristicas() {
        return carateristicas;
    }

    public String getFamilia() {
        return familia;
    }

    //Modificadores
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCarateristicas(String carateristicas) {
        this.carateristicas = carateristicas;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

}

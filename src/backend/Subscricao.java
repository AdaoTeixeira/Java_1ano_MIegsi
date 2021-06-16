package backend;

/**
 *
 * @author Adão Teixeira, Cláudia Ribeiro, José Ribeiro
 *
 */
public enum Subscricao {
    PENDENTE("Pendente"),
    ATIVA("Ativa"),
    SUSPENSA("Suspensa");

    private String estado;

    Subscricao(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}

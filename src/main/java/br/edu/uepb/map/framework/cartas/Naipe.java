package br.edu.uepb.map.framework.cartas;

/**
 * Naipes de uma carta tradicional, sem ordem ou forca associada.
 */
public enum Naipe {
    /** Copas. */
    COPAS("Copas"),
    /** Ouros. */
    OUROS("Ouros"),
    /** Espadas. */
    ESPADAS("Espadas"),
    /** Paus. */
    PAUS("Paus");

    private final String descricao;

    Naipe(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o nome do naipe adequado para exibicao.
     *
     * @return o nome do naipe
     */
    public String getDescricao() {
        return descricao;
    }
}

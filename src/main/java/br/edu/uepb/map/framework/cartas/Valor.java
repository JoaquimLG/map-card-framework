package br.edu.uepb.map.framework.cartas;

/**
 * Valores de as a rei de uma carta tradicional, sem pontuacao ou precedencia.
 */
public enum Valor {
    /** As. */
    AS("Ás"),
    /** Dois. */
    DOIS("Dois"),
    /** Tres. */
    TRES("Três"),
    /** Quatro. */
    QUATRO("Quatro"),
    /** Cinco. */
    CINCO("Cinco"),
    /** Seis. */
    SEIS("Seis"),
    /** Sete. */
    SETE("Sete"),
    /** Oito. */
    OITO("Oito"),
    /** Nove. */
    NOVE("Nove"),
    /** Dez. */
    DEZ("Dez"),
    /** Valete. */
    VALETE("Valete"),
    /** Dama. */
    DAMA("Dama"),
    /** Rei. */
    REI("Rei");

    private final String descricao;

    Valor(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna o nome do valor adequado para exibicao.
     *
     * @return o nome do valor
     */
    public String getDescricao() {
        return descricao;
    }
}

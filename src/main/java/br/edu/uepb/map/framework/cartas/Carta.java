package br.edu.uepb.map.framework.cartas;

/**
 * Contrato minimo para uma carta de jogo.
 *
 * <p>Implementacoes podem representar qualquer modelo de carta, sem a
 * obrigatoriedade de possuir naipe, valor, forca ou pontuacao.</p>
 */
public interface Carta {

    /**
     * Retorna uma descricao adequada para exibicao em console.
     *
     * @return a descricao publica da carta
     */
    String getDescricao();
}

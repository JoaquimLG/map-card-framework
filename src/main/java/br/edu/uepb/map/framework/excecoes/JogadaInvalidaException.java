package br.edu.uepb.map.framework.excecoes;

import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

/**
 * Indica que uma regra do jogo rejeitou a jogada de um jogador.
 *
 * <p>A exceção mantém as referências recebidas para que quem a tratar possa
 * identificar a tentativa que falhou. Essas referências podem ser nulas.</p>
 */
public class JogadaInvalidaException extends IllegalStateException {

    private final transient Jogador jogador;
    private final transient Jogada jogada;

    /**
     * Cria a excecao identificando o jogador e a jogada rejeitados por
     * {@link br.edu.uepb.map.framework.regras.RegraJogo#jogadaValida}.
     *
     * @param jogador jogador que tentou a jogada, pode ser {@code null}
     * @param jogada  jogada rejeitada pela regra do jogo, pode ser {@code null}
     */

    public JogadaInvalidaException(Jogador jogador, Jogada jogada) {
        super("jogada invalida para o jogador '%s'".formatted(
                jogador == null ? "desconhecido" : jogador.getNome()));
        this.jogador = jogador;
        this.jogada = jogada;
    }

    /**
     * Retorna o jogador que tentou a jogada rejeitada.
     *
     * @return o jogador informado na criação da exceção, ou {@code null}
     */
    public Jogador getJogador() {
        return jogador;
    }

    /**
     * Retorna a jogada rejeitada pela regra.
     *
     * @return a jogada informada na criação da exceção, ou {@code null}
     */
    public Jogada getJogada() {
        return jogada;
    }
}

package br.edu.uepb.map.framework.excecoes;

import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

// indica que um jogador fez uma jogada que RegraJogo não permite no estado atual da partida
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

    public Jogador getJogador() {
        return jogador;
    }

    public Jogada getJogada() {
        return jogada;
    }
}

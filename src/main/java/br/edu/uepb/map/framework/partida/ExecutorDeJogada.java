package br.edu.uepb.map.framework.partida;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

/**
 * Ponto de extensão responsável por aplicar o efeito de uma jogada já
 * validada por {@link br.edu.uepb.map.framework.regras.RegraJogo}.
 *
 * <p>Enquanto {@code RegraJogo} apenas diz se uma jogada é permitida,
 * é o {@code ExecutorDeJogada} quem efetivamente altera o estado da
 * partida em decorrência dela (comprar carta, desativar o jogador,
 * disparar eventos etc.). Cada jogo concreto implementa o efeito das
 * suas próprias jogadas (ex.: {@code ExecutorDeJogadaBlackjack}).</p>
 *
 * @param <T> subtipo de carta utilizado pelo baralho da partida
 */
@FunctionalInterface
public interface ExecutorDeJogada<T extends Carta> {

    /**
     * Aplica o efeito da jogada informada sobre o jogador e a partida.
     *
     * @param jogada  jogada já validada por {@code RegraJogo}
     * @param jogador jogador que realizou a jogada
     * @param partida partida em andamento, usada por exemplo para
     *                comprar cartas do baralho
     */
    void aplicar(Jogada jogada, Jogador jogador, Partida<T> partida);
}
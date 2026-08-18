package br.edu.uepb.map.framework.estrategia;

import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

/**
 * Ponto de extensão que define como um {@code JogadorAutomatico} decide
 * sua jogada, aplicando o padrão de projeto <b>Strategy</b>.
 *
 * <p>Cada jogo concreto fornece sua própria estratégia (ex.:
 * {@code EstrategiaBlackjack}, que faz a banca comprar carta enquanto
 * a pontuação for menor que 17). Isso permite trocar a forma de decisão
 * de um jogador automático sem alterar {@code JogadorAutomatico} nem
 * o restante do framework.</p>
 */
public interface Estrategia {

    /**
     * Decide qual jogada o jogador automático deve realizar, a partir
     * do estado atual do jogador (incluindo sua mão de cartas).
     *
     * @param jogador jogador automático que precisa decidir uma jogada
     * @return a jogada escolhida, nunca {@code null}
     */
    Jogada escolherJogada(Jogador jogador);
}
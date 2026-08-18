package br.edu.uepb.map.framework.regras;

import java.util.List;

import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

/**
 * Ponto de extensão que define as regras de um jogo de cartas.
 *
 * <p>O framework não conhece as regras de nenhum jogo específico —
 * cada jogo concreto (ex.: Blackjack) fornece sua própria implementação,
 * dizendo quais jogadas são permitidas e como se determina o vencedor.
 * A {@link br.edu.uepb.map.framework.partida.Partida} depende apenas
 * desta interface para validar jogadas e apurar o resultado.</p>
 */
public interface RegraJogo {

    /**
     * Verifica se a jogada que o jogador quer fazer é permitida no
     * estado atual da partida.
     *
     * @param jogada  jogada que o jogador deseja realizar
     * @param jogador jogador que está tentando realizar a jogada
     * @return {@code true} quando a jogada é permitida; {@code false}
     *         caso contrário
     */
    boolean jogadaValida(Jogada jogada, Jogador jogador);

    /**
     * Analisa a lista de jogadores da partida e determina quem venceu.
     *
     * @param jogadores jogadores que participaram da partida
     * @return o jogador vencedor, ou {@code null} caso haja empate ou
     *         nenhum vencedor possa ser determinado
     */
    Jogador verificarVencedor(List<Jogador> jogadores);
}
package br.edu.uepb.map.jogos.blackjack;

import java.util.List;

import br.edu.uepb.map.framework.baralho.Baralho;
import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.partida.ExecutorDeJogada;
import br.edu.uepb.map.framework.partida.Partida;
import br.edu.uepb.map.framework.regras.RegraJogo;

/**
 * Implementação concreta de uma partida de Blackjack.
 *
 * <p>Esta classe especializa {@link Partida}, que utiliza o padrão
 * de projeto Template Method para definir o fluxo geral de uma partida.</p>
 *
 * <p>A responsabilidade desta classe é fornecer as particularidades
 * do Blackjack para o algoritmo definido pela classe {@code Partida}.</p>
 *
 * <p>No Blackjack, a partida é encerrada quando nenhum jogador permanece
 * ativo.</p>
 *
 * @param <T> tipo de carta utilizado pelo baralho da partida
 */
public class PartidaBlackjack<T extends Carta>
        extends Partida<T> {

    /**
     * Cria uma nova partida de Blackjack.
     *
     * @param jogadores jogadores que participarão da partida
     * @param baralho baralho utilizado durante a partida
     * @param regra regras específicas do Blackjack
     * @param executorDeJogada executor responsável por aplicar as jogadas
     */
    public PartidaBlackjack(
            List<Jogador> jogadores,
            Baralho<T> baralho,
            RegraJogo regra,
            ExecutorDeJogada<T> executorDeJogada) {

        super(
                jogadores,
                baralho,
                regra,
                executorDeJogada);
    }

    /**
     * Define a condição de encerramento da partida de Blackjack.
     *
     * <p>A partida termina quando todos os jogadores deixam de estar
     * ativos. Um jogador pode deixar de estar ativo, por exemplo, após
     * parar ou estourar a pontuação máxima.</p>
     *
     * @return {@code true} quando nenhum jogador está ativo;
     *         {@code false} caso ainda exista algum jogador ativo
     */
    @Override
    protected boolean deveEncerrar() {
        return getJogadores()
                .stream()
                .noneMatch(Jogador::isAtivo);
    }
}
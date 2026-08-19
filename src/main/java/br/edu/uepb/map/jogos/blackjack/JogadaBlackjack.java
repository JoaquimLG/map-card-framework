package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.jogador.Jogada;

import java.util.Objects;

/**
 * Jogada específica do Blackjack: representa uma ação escolhida pelo
 * jogador.
 *
 * <p>É validada por {@link RegraBlackjack} e aplicada por
 * {@link ExecutorDeJogadaBlackjack}.</p>
 */
public class JogadaBlackjack implements Jogada {

    private final AcaoBlackjack acao;

    /**
     * Cria uma jogada com a ação informada.
     *
     * @param acao ação da jogada
     * @throws NullPointerException se a ação for {@code null}
     */
    public JogadaBlackjack(AcaoBlackjack acao) {
        this.acao = Objects.requireNonNull(acao, "acao nao pode ser nula");
    }

    /**
     * Retorna a ação desta jogada.
     *
     * @return a ação, nunca {@code null}
     */
    public AcaoBlackjack getAcao() {
        return acao;
    }
}

package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.jogador.Jogada;

import java.util.Locale;
import java.util.Objects;

/**
 * Jogada específica do Blackjack: representa uma ação escolhida pelo
 * jogador ("COMPRAR" ou "PARAR"), normalizada no construtor.
 *
 * <p>É validada por {@link RegraBlackjack} e aplicada por
 * {@link ExecutorDeJogadaBlackjack}.</p>
 */
public class JogadaBlackjack implements Jogada {

    private final String acao;

    /**
     * Cria uma jogada com a ação informada, normalizada removendo
     * espaços nas pontas e convertendo para maiúsculas (ex.: "
     * comprar " vira "COMPRAR").
     *
     * @param acao ação da jogada
     * @throws NullPointerException se a ação for {@code null}
     */
    public JogadaBlackjack(String acao) {
        this.acao = Objects.requireNonNull(acao, "acao nao pode ser nula")
                .trim().toUpperCase(Locale.ROOT);
    }

    /**
     * Retorna a ação desta jogada, já normalizada (ex.: "COMPRAR",
     * "PARAR").
     *
     * @return a ação, nunca {@code null}
     */
    public String getAcao() {
        return acao;
    }
}

package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.estrategia.Estrategia;

import java.util.Objects;

/**
 * Jogador cuja jogada é decidida automaticamente por uma
 * {@link Estrategia}, sem interação com o console. No Blackjack,
 * representa o dealer/banca.
 */
public class JogadorAutomatico extends Jogador {

    private final Estrategia estrategia;

    /**
     * Cria um jogador automático que decide suas jogadas através da
     * estratégia informada.
     *
     * @param nome        nome do jogador
     * @param maoDeCartas mão de cartas do jogador
     * @param estrategia  estratégia usada para decidir cada jogada
     * @throws NullPointerException     se algum argumento for
     *                                  {@code null}
     * @throws IllegalArgumentException se o nome for vazio ou em branco
     */
    public JogadorAutomatico(String nome, MaoDeCartas<Carta> maoDeCartas, Estrategia estrategia) {
        super(nome, maoDeCartas);
        this.estrategia = Objects.requireNonNull(estrategia, "estrategia nao pode ser nula");
    }

    /**
     * Delega a decisão da jogada para a {@link Estrategia} configurada.
     *
     * @return a jogada escolhida pela estratégia, nunca {@code null}
     * @throws NullPointerException se a estratégia produzir uma jogada
     *                               nula
     */
    @Override
    public Jogada decidirJogada() {
        return Objects.requireNonNull(estrategia.escolherJogada(this),
                "estrategia nao pode produzir uma jogada nula");
    }
}

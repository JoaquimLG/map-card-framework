package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;
import java.util.Scanner;
import java.util.Objects;
import java.util.function.Function;

/**
 * Jogador que recebe sua jogada digitada pelo usuário no console.
 *
 * <p>O framework em si não sabe interpretar o texto digitado — essa
 * conversão é responsabilidade do {@code mapeadorDeJogada} fornecido
 * pelo jogo concreto (ex.: transformar "c" em uma jogada de compra no
 * Blackjack).</p>
 */
public class JogadorHumano extends Jogador {

    private final Scanner scanner;
    // Função que converte o texto digitado do usuário em uma Jogada concreta do jogo
    private final Function<String, Jogada> mapeadorDeJogada;

    /**
     * Cria um jogador humano que lê sua jogada do console.
     *
     * @param nome             nome do jogador
     * @param maoDeCartas      mão de cartas do jogador
     * @param scanner          scanner usado para ler a entrada do
     *                         console
     * @param mapeadorDeJogada função que converte o texto digitado na
     *                         jogada correspondente do jogo específico
     * @throws NullPointerException     se algum argumento for
     *                                  {@code null}
     * @throws IllegalArgumentException se o nome for vazio ou em branco
     */
    public JogadorHumano(String nome, MaoDeCartas<Carta> maoDeCartas, Scanner scanner, Function<String, Jogada> mapeadorDeJogada) {
        super(nome, maoDeCartas);
        this.scanner = Objects.requireNonNull(scanner, "scanner nao pode ser nulo");
        this.mapeadorDeJogada = Objects.requireNonNull(
                mapeadorDeJogada, "mapeadorDeJogada nao pode ser nulo");
    }

    /**
     * Aguarda uma linha de entrada no console e a converte na jogada
     * correspondente através do {@code mapeadorDeJogada} configurado.
     *
     * @return a jogada correspondente à entrada digitada, nunca
     *         {@code null}
     * @throws NullPointerException se o mapeador produzir uma jogada
     *                              nula
     */
    @Override
    public Jogada decidirJogada() {
        // O framework apenas aguarda a entrada no console.
        String entrada = scanner.nextLine();

        // Converte a string digitada na jogada correspondente do jogo específico
        return Objects.requireNonNull(mapeadorDeJogada.apply(entrada),
                "mapeadorDeJogada nao pode produzir uma jogada nula");
    }
}

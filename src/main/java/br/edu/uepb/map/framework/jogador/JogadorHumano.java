package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;
import java.util.Scanner;
import java.util.function.Function;

public class JogadorHumano extends Jogador {

    private final Scanner scanner;
    // Função que converte o texto digitado do usuário em uma Jogada concreta do jogo
    private final Function<String, Jogada> mapeadorDeJogada;

    public JogadorHumano(String nome, MaoDeCartas<Carta> maoDeCartas, Scanner scanner, Function<String, Jogada> mapeadorDeJogada) {
        super(nome, maoDeCartas);
        this.scanner = scanner;
        this.mapeadorDeJogada = mapeadorDeJogada;
    }

    @Override
    public Jogada decidirJogada() {
        // O framework apenas aguarda a entrada no console.
        String entrada = scanner.nextLine();

        // Converte a string digitada na jogada correspondente do jogo específico
        return mapeadorDeJogada.apply(entrada);
    }
}
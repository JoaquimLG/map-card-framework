package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.estrategia.Estrategia;

import java.util.Objects;

public class JogadorAutomatico extends Jogador {

    private final Estrategia estrategia;

    public JogadorAutomatico(String nome, MaoDeCartas<Carta> maoDeCartas, Estrategia estrategia) {
        super(nome, maoDeCartas);
        this.estrategia = Objects.requireNonNull(estrategia, "estrategia nao pode ser nula");
    }

    @Override
    public Jogada decidirJogada() {
        return Objects.requireNonNull(estrategia.escolherJogada(this),
                "estrategia nao pode produzir uma jogada nula");
    }
}

package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.estrategia.Estrategia;

public class JogadorAutomatico extends Jogador {

    private final Estrategia estrategia;

    public JogadorAutomatico(String nome, MaoDeCartas<Carta> maoDeCartas, Estrategia estrategia) {
        super(nome, maoDeCartas);
        this.estrategia = estrategia;
    }

    @Override
    public Jogada decidirJogada() {
        return estrategia.escolherJogada(this);
    }
}

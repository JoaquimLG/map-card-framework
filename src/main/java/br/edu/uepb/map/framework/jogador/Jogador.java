package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;

public abstract class Jogador {

    private final String nome;
    private final MaoDeCartas<Carta> maoDeCartas;
    private boolean ativo;

    protected Jogador(String nome, MaoDeCartas<Carta> maoDeCartas) {
        this.nome = nome;
        this.maoDeCartas = maoDeCartas;
        this.ativo = true;
    }

    public String getNome() {
        return nome;
    }

    public MaoDeCartas<Carta> getMaoDeCartas() {
        return maoDeCartas;
    }

    //Indica se o jogador ainda participa ativamente da rodada atual
    public boolean isAtivo() {
        return ativo;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void receberCarta(Carta carta) {
        maoDeCartas.receberCarta(carta);
    }

    public abstract Jogada decidirJogada();
}

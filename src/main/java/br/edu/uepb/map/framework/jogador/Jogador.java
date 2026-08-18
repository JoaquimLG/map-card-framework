package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;

import java.util.Objects;

public abstract class Jogador {

    private final String nome;
    private final MaoDeCartas<Carta> maoDeCartas;
    private boolean ativo;

    protected Jogador(String nome, MaoDeCartas<Carta> maoDeCartas) {
        this.nome = Objects.requireNonNull(nome, "nome nao pode ser nulo");
        if (nome.isBlank()) {
            throw new IllegalArgumentException("nome nao pode ser vazio");
        }
        this.maoDeCartas = Objects.requireNonNull(maoDeCartas, "maoDeCartas nao pode ser nula");
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

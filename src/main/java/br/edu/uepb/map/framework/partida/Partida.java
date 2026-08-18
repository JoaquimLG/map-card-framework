package br.edu.uepb.map.framework.partida;

import br.edu.uepb.map.framework.baralho.Baralho;
import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.excecoes.JogadaInvalidaException;
import br.edu.uepb.map.framework.excecoes.CartasInsuficientesException;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.regras.RegraJogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Partida<T extends Carta> {

    private final List<Jogador> jogadores;
    private final Baralho<T> baralho;
    private final RegraJogo regra;
    private final ExecutorDeJogada<T> executorDeJogada;

    private int indiceJogadorAtual;
    private boolean iniciada;
    private boolean encerrada;
    private Jogador vencedor;

    public Partida(List<Jogador> jogadores, Baralho<T> baralho, RegraJogo regra,
                   ExecutorDeJogada<T> executorDeJogada) {
        Objects.requireNonNull(jogadores, "jogadores nao pode ser nulo");
        this.jogadores = new ArrayList<>(jogadores);
        if (this.jogadores.isEmpty()) {
            throw new IllegalArgumentException("partida precisa de ao menos um jogador");
        }
        if (this.jogadores.contains(null)) {
            throw new NullPointerException("jogadores nao pode conter elementos nulos");
        }
        this.baralho = Objects.requireNonNull(baralho, "baralho nao pode ser nulo");
        this.regra = Objects.requireNonNull(regra, "regra nao pode ser nula");
        this.executorDeJogada = Objects.requireNonNull(executorDeJogada, "executorDeJogada nao pode ser nulo");
        this.indiceJogadorAtual = 0;
    }

    public void iniciar(int cartasIniciaisPorJogador) {
        if (iniciada) {
            throw new IllegalStateException("partida ja foi iniciada");
        }
        if (encerrada) {
            throw new IllegalStateException("partida ja encerrada");
        }
        if (cartasIniciaisPorJogador < 0) {
            throw new IllegalArgumentException("cartasIniciaisPorJogador nao pode ser negativo");
        }
        long quantidadeNecessaria = (long) cartasIniciaisPorJogador * jogadores.size();
        if (quantidadeNecessaria > baralho.tamanho()) {
            throw new CartasInsuficientesException(
                    quantidadeNecessaria > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) quantidadeNecessaria,
                    baralho.tamanho());
        }
        for (Jogador jogador : jogadores) {
            for (int i = 0; i < cartasIniciaisPorJogador; i++) {
                comprarCartaPara(jogador);
            }
        }
        this.iniciada = true;
        this.indiceJogadorAtual = 0;
    }

    public T comprarCartaPara(Jogador jogador) {
        Objects.requireNonNull(jogador, "jogador nao pode ser nulo");
        if (!jogadores.contains(jogador)) {
            throw new IllegalArgumentException("jogador nao participa desta partida");
        }
        T carta = baralho.comprarCarta();
        jogador.receberCarta(carta);
        return carta;
    }

    public Jogador getJogadorDaVez() {
        return jogadores.get(indiceJogadorAtual);
    }

    public void jogarTurno() {
        if (!iniciada) {
            throw new IllegalStateException("partida ainda nao foi iniciada; chame iniciar(int) antes");
        }
        if (encerrada) {
            throw new IllegalStateException("partida ja encerrada");
        }

        Jogador jogadorAtual = getJogadorDaVez();

        if (!jogadorAtual.isAtivo()) {
            avancarTurno();
            return;
        }

        Jogada jogada = Objects.requireNonNull(jogadorAtual.decidirJogada(),
                "jogador nao pode decidir uma jogada nula");

        if (!regra.jogadaValida(jogada, jogadorAtual)) {
            throw new JogadaInvalidaException(jogadorAtual, jogada);
        }

        executorDeJogada.aplicar(jogada, jogadorAtual, this);

        if (deveEncerrar()) {
            encerrarPartida();
            return;
        }

        avancarTurno();
    }

    private void avancarTurno() {
        if (jogadores.stream().noneMatch(Jogador::isAtivo)) {
            return;
        }
        do {
            indiceJogadorAtual = (indiceJogadorAtual + 1) % jogadores.size();
        } while (!jogadores.get(indiceJogadorAtual).isAtivo());
    }

    protected boolean deveEncerrar() {
        return jogadores.stream().noneMatch(Jogador::isAtivo);
    }

    public void encerrarPartida() {
        if (!iniciada) {
            throw new IllegalStateException("partida ainda nao foi iniciada");
        }
        if (encerrada) {
            throw new IllegalStateException("partida ja encerrada");
        }
        Jogador vencedorApurado = regra.verificarVencedor(List.copyOf(jogadores));
        this.vencedor = vencedorApurado;
        this.encerrada = true;
    }

    public boolean isIniciada() {
        return iniciada;
    }

    public boolean isEncerrada() {
        return encerrada;
    }

    public Jogador getVencedor() {
        return vencedor;
    }

    public List<Jogador> getJogadores() {
        return List.copyOf(jogadores);
    }

    public Baralho<T> getBaralho() {
        return baralho;
    }
}

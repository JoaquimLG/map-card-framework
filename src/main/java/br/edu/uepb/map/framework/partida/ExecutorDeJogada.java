package br.edu.uepb.map.framework.partida;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

// Aplica o efeito de uma jogada ja validada por RegraJogo
// Cada jogo implementa o efeito das suas proprias jogadas.
@FunctionalInterface
public interface ExecutorDeJogada<T extends Carta> {
    void aplicar(Jogada jogada, Jogador jogador, Partida<T> partida);
}
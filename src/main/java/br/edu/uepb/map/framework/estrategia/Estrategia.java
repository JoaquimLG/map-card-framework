package br.edu.uepb.map.framework.estrategia;

import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

public interface Estrategia {
    
    // Recebe o jogador (que contém a mão de cartas) e decide qual jogada fazer
    Jogada escolherJogada(Jogador jogador);
}
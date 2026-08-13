package br.edu.uepb.map.framework.regras;

import java.util.List;

import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

public interface RegraJogo {
    
    // Verifica se a jogada que o jogador quer fazer é permitida no momento
    boolean jogadaValida(Jogada jogada, Jogador jogador);
    
    // Analisa a lista de jogadores da partida e retorna quem venceu (ou null se houver empate/ninguém)
    Jogador verificarVencedor(List<Jogador> jogadores);
}
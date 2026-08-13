package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.jogador.Jogada;

// Nota: Se a classe Jogada base for uma interface, use "implements Jogada".
// Se for uma classe abstrata, use "extends Jogada". O erro costuma aparecer para ambos.
public class JogadaBlackjack implements Jogada { 
    
    private String acao;

    public JogadaBlackjack(String acao) {
        this.acao = acao;
    }

    public String getAcao() {
        return acao;
    }
}
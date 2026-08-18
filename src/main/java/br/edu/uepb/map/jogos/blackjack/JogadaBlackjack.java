package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.jogador.Jogada;

import java.util.Locale;
import java.util.Objects;

// Nota: Se a classe Jogada base for uma interface, use "implements Jogada".
// Se for uma classe abstrata, use "extends Jogada". O erro costuma aparecer para ambos.
public class JogadaBlackjack implements Jogada { 
    
    private final String acao;

    public JogadaBlackjack(String acao) {
        this.acao = Objects.requireNonNull(acao, "acao nao pode ser nula")
                .trim().toUpperCase(Locale.ROOT);
    }

    public String getAcao() {
        return acao;
    }
}

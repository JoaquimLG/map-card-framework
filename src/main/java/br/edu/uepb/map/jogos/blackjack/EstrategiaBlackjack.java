package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.estrategia.Estrategia;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

public class EstrategiaBlackjack implements Estrategia {

    private final CalculadoraDeMao calculadora = new CalculadoraDeMao();

    @Override
    public Jogada escolherJogada(Jogador jogador) {
        int pontuacaoAtual = calculadora.calcularPontuacao(jogador);

        // A banca compra enquanto tiver menos de 17 pontos.
        if (pontuacaoAtual < 17) {
            return new JogadaBlackjack("COMPRAR");
        }

        // Com 17 ou mais, a banca para.
        return new JogadaBlackjack("PARAR");
    }
}
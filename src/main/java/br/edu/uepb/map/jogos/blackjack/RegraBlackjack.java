package br.edu.uepb.map.jogos.blackjack;

import java.util.List;

import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.regras.RegraJogo;

public class RegraBlackjack implements RegraJogo {

    private final CalculadoraDeMao calculadora = new CalculadoraDeMao();

    @Override
    public boolean jogadaValida(Jogada jogada, Jogador jogador) {
        // Jogador que ja parou ou ja estourou (ver Jogador.desativar())
        // nao pode mais jogar.
        if (!jogador.isAtivo()) {
            return false;
        }

        if (!(jogada instanceof JogadaBlackjack)) {
            return false;
        }

        JogadaBlackjack jogadaBlackjack = (JogadaBlackjack) jogada;
        String acao = jogadaBlackjack.getAcao();

        int pontuacao = calculadora.calcularPontuacao(jogador);

        // Se o jogador já estourou 21, não pode mais comprar cartas.
        if (pontuacao > 21 && acao.equals("COMPRAR")) {
            return false;
        }

        // Se chegou a 21, também não pode comprar mais cartas.
        if (pontuacao == 21 && acao.equals("COMPRAR")) {
            return false;
        }

        // As jogadas permitidas no Blackjack são COMPRAR e PARAR.
        return acao.equals("COMPRAR") || acao.equals("PARAR");
    }

    @Override
    public Jogador verificarVencedor(List<Jogador> jogadores) {
        if (jogadores == null || jogadores.isEmpty()) {
            return null;
        }

        Jogador vencedor = null;
        int maiorPontuacao = -1;

        for (Jogador jogador : jogadores) {
            int pontuacao = calculadora.calcularPontuacao(jogador);

            // Jogadores que estouraram 21 perdem.
            if (pontuacao > 21) {
                continue;
            }

            // Se encontrou uma pontuação maior, esse jogador passa a ser
            // o vencedor provisório.
            if (pontuacao > maiorPontuacao) {
                maiorPontuacao = pontuacao;
                vencedor = jogador;
            } else if (pontuacao == maiorPontuacao) {
                // Empate: não existe vencedor único.
                vencedor = null;
            }
        }

        return vencedor;
    }
}
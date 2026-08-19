package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.estrategia.Estrategia;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;

/**
 * Estratégia da banca no Blackjack: compra carta enquanto a pontuação
 * for menor que 17, e para assim que atingir 17 ou mais. Usada por
 * {@link br.edu.uepb.map.framework.jogador.JogadorAutomatico} para
 * decidir a jogada de um jogador automático.
 */
public class EstrategiaBlackjack implements Estrategia {

    private final CalculadoraDeMao calculadora = new CalculadoraDeMao();

    /**
     * Cria uma estratégia de banca, sem estado próprio além da
     * calculadora de pontuação.
     */
    public EstrategiaBlackjack() {
    }

    /**
     * Decide COMPRAR quando a pontuação atual do jogador for menor
     * que 17, ou PARAR caso contrário.
     *
     * @param jogador jogador automático que precisa decidir uma jogada
     * @return a jogada escolhida, nunca {@code null}
     */
    @Override
    public Jogada escolherJogada(Jogador jogador) {
        int pontuacaoAtual = calculadora.calcularPontuacao(jogador);

        // A banca compra enquanto tiver menos de 17 pontos.
        if (pontuacaoAtual < 17) {
            return new JogadaBlackjack(AcaoBlackjack.COMPRAR);
        }

        // Com 17 ou mais, a banca para.
        return new JogadaBlackjack(AcaoBlackjack.PARAR);
    }
}

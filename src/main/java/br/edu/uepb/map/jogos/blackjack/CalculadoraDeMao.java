package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.cartas.CartaTradicional;
import br.edu.uepb.map.framework.cartas.Valor;
import br.edu.uepb.map.framework.jogador.Jogador;

import java.util.Objects;

/**
 * Calcula a pontuacao da mao de um jogador no Blackjack (As vale 1 ou 11,
 * J/Q/K valem 10).
 *
 * <p>Extraida de RegraBlackjack e EstrategiaBlackjack para nao duplicar essa
 * logica nas duas classes.</p>
 *
 * <p><b>Atencao, time:</b> pelo guia de construcao, essa conta e
 * responsabilidade do Jeff (classe CalculadoraDeMao). Combinar com ele se a
 * versao dele deve substituir esta — mantendo a assinatura
 * {@link #calcularPontuacao(Jogador)}, que e o que RegraBlackjack e
 * EstrategiaBlackjack chamam.</p>
 */
public class CalculadoraDeMao {

    public int calcularPontuacao(Jogador jogador) {
        Objects.requireNonNull(jogador, "jogador nao pode ser nulo");
        int pontuacao = 0;
        int quantidadeDeAses = 0;

        for (Carta carta : jogador.getMaoDeCartas().listarCartas()) {

            if (!(carta instanceof CartaTradicional cartaTradicional)) {
                throw new IllegalArgumentException(
                        "Blackjack aceita apenas cartas tradicionais: " + carta.getClass().getName());
            }
            Valor valor = cartaTradicional.getValor();

            switch (valor) {
                case AS:
                    pontuacao += 11;
                    quantidadeDeAses++;
                    break;

                case DOIS:
                    pontuacao += 2;
                    break;

                case TRES:
                    pontuacao += 3;
                    break;

                case QUATRO:
                    pontuacao += 4;
                    break;

                case CINCO:
                    pontuacao += 5;
                    break;

                case SEIS:
                    pontuacao += 6;
                    break;

                case SETE:
                    pontuacao += 7;
                    break;

                case OITO:
                    pontuacao += 8;
                    break;

                case NOVE:
                    pontuacao += 9;
                    break;

                case DEZ:
                case VALETE:
                case DAMA:
                case REI:
                    pontuacao += 10;
                    break;
            }
        }

        // Se o As fizer a mao passar de 21, ele passa de 11 para 1.
        while (pontuacao > 21 && quantidadeDeAses > 0) {
            pontuacao -= 10;
            quantidadeDeAses--;
        }

        return pontuacao;
    }
}

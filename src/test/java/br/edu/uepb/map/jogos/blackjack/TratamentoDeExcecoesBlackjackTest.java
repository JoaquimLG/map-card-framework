package br.edu.uepb.map.jogos.blackjack;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import br.edu.uepb.map.framework.baralho.Baralho;
import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.eventos.NotificadorEventos;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.jogador.JogadorAutomatico;
import br.edu.uepb.map.framework.jogador.MaoDeCartas;
import br.edu.uepb.map.framework.partida.Partida;

class TratamentoDeExcecoesBlackjackTest {

    private record CartaIncompativel(String descricao) implements Carta {
        @Override
        public String getDescricao() {
            return descricao;
        }
    }

    @Test
    void jogadaNormalizaAcaoERejeitaNulo() {
        assertEquals("COMPRAR", new JogadaBlackjack("  comprar ").getAcao());
        assertThrows(NullPointerException.class, () -> new JogadaBlackjack(null));
    }

    @Test
    void calculadoraRejeitaJogadorNuloECartaIncompativel() {
        CalculadoraDeMao calculadora = new CalculadoraDeMao();
        assertThrows(NullPointerException.class, () -> calculadora.calcularPontuacao(null));

        Jogador jogador = new JogadorAutomatico(
                "Jogador", new MaoDeCartas<>(List.of(new CartaIncompativel("outra"))),
                ignorado -> new JogadaBlackjack("PARAR"));
        assertThrows(IllegalArgumentException.class, () -> calculadora.calcularPontuacao(jogador));
    }

    @Test
    void executorRejeitaDependenciaNulaETipoDeJogadaIncorretoSemClassCastException() {
        assertThrows(NullPointerException.class, () -> new ExecutorDeJogadaBlackjack<Carta>(null));

        Jogador jogador = new JogadorAutomatico(
                "Jogador", new MaoDeCartas<>(), ignorado -> new JogadaBlackjack("PARAR"));
        Partida<Carta> partida = new PartidaBlackjack<>(List.of(jogador), new Baralho<>(),
                new RegraBlackjack(), new ExecutorDeJogadaBlackjack<>(new NotificadorEventos()));
        Jogada incompativel = new Jogada() { };

        ExecutorDeJogadaBlackjack<Carta> executor =
                new ExecutorDeJogadaBlackjack<>(new NotificadorEventos());
        assertThrows(IllegalArgumentException.class,
                () -> executor.aplicar(incompativel, jogador, partida));
    }
}

package br.edu.uepb.map.framework.partida;

import br.edu.uepb.map.framework.baralho.Baralho;
import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.cartas.CartaTradicional;
import br.edu.uepb.map.framework.cartas.Naipe;
import br.edu.uepb.map.framework.cartas.Valor;
import br.edu.uepb.map.framework.excecoes.BaralhoVazioException;
import br.edu.uepb.map.framework.excecoes.JogadaInvalidaException;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.jogador.JogadorAutomatico;
import br.edu.uepb.map.framework.jogador.JogadorHumano;
import br.edu.uepb.map.framework.jogador.MaoDeCartas;
import br.edu.uepb.map.jogos.blackjack.CalculadoraDeMao;
import br.edu.uepb.map.jogos.blackjack.EstrategiaBlackjack;
import br.edu.uepb.map.jogos.blackjack.JogadaBlackjack;
import br.edu.uepb.map.jogos.blackjack.RegraBlackjack;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PartidaTest {

    // Executor de teste equivalente ao do Blackjack: COMPRAR compra carta
    // (e para sozinho se chegar a >=21), PARAR desativa o jogador.
    private static class ExecutorTeste implements ExecutorDeJogada<CartaTradicional> {
        private final CalculadoraDeMao calculadora = new CalculadoraDeMao();

        @Override
        public void aplicar(Jogada jogada, Jogador jogador, Partida<CartaTradicional> partida) {
            JogadaBlackjack j = (JogadaBlackjack) jogada;
            if (j.getAcao().equals("COMPRAR")) {
                partida.comprarCartaPara(jogador);
                if (calculadora.calcularPontuacao(jogador) >= 21) {
                    jogador.desativar();
                }
            } else {
                jogador.desativar();
            }
        }
    }

    private Jogador jogadorHumano(String nome, MaoDeCartas<Carta> mao) {
        return new JogadorHumano(nome, mao, new Scanner(System.in), JogadaBlackjack::new);
    }

    @Test
    void rejeitaListaDeJogadoresVazia() {
        assertThrows(IllegalArgumentException.class,
                () -> new Partida<>(List.of(), new Baralho<CartaTradicional>(), new RegraBlackjack(), new ExecutorTeste()));
    }

    @Test
    void iniciarDistribuiCartasIniciaisEMarcaComoIniciada() {
        Jogador jogador = jogadorHumano("Jogador", new MaoDeCartas<>());
        Jogador banca = new JogadorAutomatico("Banca", new MaoDeCartas<>(), new EstrategiaBlackjack());

        Baralho<CartaTradicional> baralho = new Baralho<>(List.of(
                new CartaTradicional(Naipe.COPAS, Valor.DOIS),
                new CartaTradicional(Naipe.COPAS, Valor.TRES),
                new CartaTradicional(Naipe.COPAS, Valor.QUATRO),
                new CartaTradicional(Naipe.COPAS, Valor.CINCO)
        ));

        Partida<CartaTradicional> partida = new Partida<>(List.of(jogador, banca), baralho, new RegraBlackjack(), new ExecutorTeste());

        assertFalse(partida.isIniciada());
        partida.iniciar(2);

        assertTrue(partida.isIniciada());
        assertEquals(2, jogador.getMaoDeCartas().tamanho());
        assertEquals(2, banca.getMaoDeCartas().tamanho());
        assertEquals(0, baralho.tamanho());
        assertSame(jogador, partida.getJogadorDaVez());
    }

    @Test
    void jogarTurnoPulaJogadorInativoEDepoisEncerraQuandoTodosParam() {
        Jogador jogador = jogadorHumano("Jogador", new MaoDeCartas<>());
        Jogador banca = new JogadorAutomatico("Banca", new MaoDeCartas<>(), new EstrategiaBlackjack());

        // ordem de compra (do topo pro fundo): CINCO, QUATRO, SETE, REI
        Baralho<CartaTradicional> baralho = new Baralho<>(List.of(
                new CartaTradicional(Naipe.OUROS, Valor.REI),
                new CartaTradicional(Naipe.PAUS, Valor.SETE),
                new CartaTradicional(Naipe.ESPADAS, Valor.QUATRO),
                new CartaTradicional(Naipe.COPAS, Valor.CINCO)
        ));

        Partida<CartaTradicional> partida = new Partida<>(List.of(jogador, banca), baralho, new RegraBlackjack(), new ExecutorTeste());
        partida.iniciar(2); // jogador fica com 9, banca com 17

        jogador.desativar();
        partida.jogarTurno(); // jogador inativo: so passa a vez

        assertSame(banca, partida.getJogadorDaVez());
        assertFalse(partida.isEncerrada());

        partida.jogarTurno(); // banca com 17 para sozinha -> todos inativos -> encerra

        assertTrue(partida.isEncerrada());
        assertSame(banca, partida.getVencedor());
    }

    @Test
    void encerrarPartidaApuraVencedorViaRegraDoJogo() {
        MaoDeCartas<Carta> maoJogador = new MaoDeCartas<>(List.of(
                new CartaTradicional(Naipe.COPAS, Valor.DEZ),
                new CartaTradicional(Naipe.ESPADAS, Valor.OITO)
        ));
        MaoDeCartas<Carta> maoBanca = new MaoDeCartas<>(List.of(
                new CartaTradicional(Naipe.PAUS, Valor.DEZ),
                new CartaTradicional(Naipe.OUROS, Valor.SEIS)
        ));

        Jogador jogador = jogadorHumano("Jogador", maoJogador);
        Jogador banca = new JogadorAutomatico("Banca", maoBanca, new EstrategiaBlackjack());

        Partida<CartaTradicional> partida = new Partida<>(List.of(jogador, banca), new Baralho<>(), new RegraBlackjack(), new ExecutorTeste());
        partida.iniciar(0);
        partida.encerrarPartida();

        assertTrue(partida.isEncerrada());
        assertSame(jogador, partida.getVencedor());
    }

    @Test
    void jogarTurnoLancaJogadaInvalidaExceptionQuandoRegraRecusaAJogada() {
        MaoDeCartas<Carta> mao = new MaoDeCartas<>(List.of(
                new CartaTradicional(Naipe.COPAS, Valor.REI),
                new CartaTradicional(Naipe.ESPADAS, Valor.DEZ),
                new CartaTradicional(Naipe.PAUS, Valor.CINCO) // 25: estourou
        ));
        Jogador jogadorEstourado = new JogadorHumano("Jogador", mao, new Scanner(System.in), acao -> new JogadaBlackjack("COMPRAR"));
        Jogador banca = new JogadorAutomatico("Banca", new MaoDeCartas<>(), new EstrategiaBlackjack());

        Partida<CartaTradicional> partida = new Partida<>(List.of(jogadorEstourado, banca), new Baralho<>(), new RegraBlackjack(), new ExecutorTeste());
        partida.iniciar(0);

        assertThrows(JogadaInvalidaException.class, partida::jogarTurno);
    }

    @Test
    void jogarTurnoAntesDeIniciarLancaIllegalStateException() {
        Jogador jogador = jogadorHumano("Jogador", new MaoDeCartas<>());
        Partida<CartaTradicional> partida = new Partida<>(List.of(jogador), new Baralho<>(), new RegraBlackjack(), new ExecutorTeste());

        assertThrows(IllegalStateException.class, partida::jogarTurno);
    }

    @Test
    void comprarCartaParaPropagaBaralhoVazioException() {
        Jogador jogador = jogadorHumano("Jogador", new MaoDeCartas<>());
        Partida<CartaTradicional> partida = new Partida<>(List.of(jogador), new Baralho<>(), new RegraBlackjack(), new ExecutorTeste());

        assertThrows(BaralhoVazioException.class, () -> partida.comprarCartaPara(jogador));
    }
}
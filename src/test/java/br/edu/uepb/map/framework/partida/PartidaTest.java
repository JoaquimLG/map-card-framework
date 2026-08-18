package br.edu.uepb.map.framework.partida;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

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

class PartidaTest {

    /**
     * Implementação concreta de Partida utilizada exclusivamente
     * para testar o comportamento definido pela classe abstrata.
     *
     * <p>A implementação utiliza a mesma condição de encerramento
     * adotada pelo Blackjack: a partida termina quando nenhum
     * jogador permanece ativo.</p>
     */
    private static class PartidaDeTeste
            extends Partida<CartaTradicional> {

        /**
         * Cria uma partida utilizada nos testes.
         *
         * @param jogadores jogadores da partida
         * @param baralho baralho utilizado
         * @param regra regras utilizadas pela partida
         * @param executorDeJogada executor das jogadas
         */
        PartidaDeTeste(
                List<Jogador> jogadores,
                Baralho<CartaTradicional> baralho,
                RegraBlackjack regra,
                ExecutorDeJogada<CartaTradicional> executorDeJogada) {

            super(
                    jogadores,
                    baralho,
                    regra,
                    executorDeJogada);
        }

        /**
         * Define que a partida de teste termina quando
         * todos os jogadores estão inativos.
         *
         * @return true quando nenhum jogador está ativo
         */
        @Override
        protected boolean deveEncerrar() {
            return getJogadores()
                    .stream()
                    .noneMatch(Jogador::isAtivo);
        }
    }

    /**
     * Executor de teste equivalente ao do Blackjack.
     *
     * <p>COMPRAR compra uma carta e desativa o jogador
     * quando sua pontuação chega a pelo menos 21.
     * PARAR desativa o jogador.</p>
     */
    private static class ExecutorTeste
            implements ExecutorDeJogada<CartaTradicional> {

        private final CalculadoraDeMao calculadora =
                new CalculadoraDeMao();

        @Override
        public void aplicar(
                Jogada jogada,
                Jogador jogador,
                Partida<CartaTradicional> partida) {

            JogadaBlackjack j =
                    (JogadaBlackjack) jogada;

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

    /**
     * Cria um jogador humano utilizado nos testes.
     */
    private Jogador jogadorHumano(
            String nome,
            MaoDeCartas<Carta> mao) {

        return new JogadorHumano(
                nome,
                mao,
                new Scanner(System.in),
                JogadaBlackjack::new);
    }

    @Test
    void rejeitaListaDeJogadoresVazia() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new PartidaDeTeste(
                        List.of(),
                        new Baralho<CartaTradicional>(),
                        new RegraBlackjack(),
                        new ExecutorTeste()));
    }

    @Test
    void iniciarDistribuiCartasIniciaisEMarcaComoIniciada() {

        Jogador jogador =
                jogadorHumano(
                        "Jogador",
                        new MaoDeCartas<>());

        Jogador banca =
                new JogadorAutomatico(
                        "Banca",
                        new MaoDeCartas<>(),
                        new EstrategiaBlackjack());

        Baralho<CartaTradicional> baralho =
                new Baralho<>(List.of(
                        new CartaTradicional(
                                Naipe.COPAS,
                                Valor.DOIS),
                        new CartaTradicional(
                                Naipe.COPAS,
                                Valor.TRES),
                        new CartaTradicional(
                                Naipe.COPAS,
                                Valor.QUATRO),
                        new CartaTradicional(
                                Naipe.COPAS,
                                Valor.CINCO)
                ));

        Partida<CartaTradicional> partida =
                new PartidaDeTeste(
                        List.of(jogador, banca),
                        baralho,
                        new RegraBlackjack(),
                        new ExecutorTeste());

        assertFalse(
                partida.isIniciada());

        partida.iniciar(2);

        assertTrue(
                partida.isIniciada());

        assertEquals(
                2,
                jogador.getMaoDeCartas().tamanho());

        assertEquals(
                2,
                banca.getMaoDeCartas().tamanho());

        assertEquals(
                0,
                baralho.tamanho());

        assertSame(
                jogador,
                partida.getJogadorDaVez());
    }

    @Test
    void jogarTurnoPulaJogadorInativoEDepoisEncerraQuandoTodosParam() {

        Jogador jogador =
                jogadorHumano(
                        "Jogador",
                        new MaoDeCartas<>());

        Jogador banca =
                new JogadorAutomatico(
                        "Banca",
                        new MaoDeCartas<>(),
                        new EstrategiaBlackjack());

        /*
         * Ordem de compra do topo para o fundo:
         *
         * CINCO, QUATRO, SETE, REI
         */
        Baralho<CartaTradicional> baralho =
                new Baralho<>(List.of(
                        new CartaTradicional(
                                Naipe.OUROS,
                                Valor.REI),
                        new CartaTradicional(
                                Naipe.PAUS,
                                Valor.SETE),
                        new CartaTradicional(
                                Naipe.ESPADAS,
                                Valor.QUATRO),
                        new CartaTradicional(
                                Naipe.COPAS,
                                Valor.CINCO)
                ));

        Partida<CartaTradicional> partida =
                new PartidaDeTeste(
                        List.of(jogador, banca),
                        baralho,
                        new RegraBlackjack(),
                        new ExecutorTeste());

        partida.iniciar(2);

        jogador.desativar();

        /*
         * Jogador inativo: apenas passa a vez.
         */
        partida.jogarTurno();

        assertSame(
                banca,
                partida.getJogadorDaVez());

        assertFalse(
                partida.isEncerrada());

        /*
         * Banca com 17 pontos para sozinha.
         * Todos ficam inativos e a partida encerra.
         */
        partida.jogarTurno();

        assertTrue(
                partida.isEncerrada());

        assertSame(
                banca,
                partida.getVencedor());
    }

    @Test
    void encerrarPartidaApuraVencedorViaRegraDoJogo() {

        MaoDeCartas<Carta> maoJogador =
                new MaoDeCartas<>(List.of(
                        new CartaTradicional(
                                Naipe.COPAS,
                                Valor.DEZ),
                        new CartaTradicional(
                                Naipe.ESPADAS,
                                Valor.OITO)
                ));

        MaoDeCartas<Carta> maoBanca =
                new MaoDeCartas<>(List.of(
                        new CartaTradicional(
                                Naipe.PAUS,
                                Valor.DEZ),
                        new CartaTradicional(
                                Naipe.OUROS,
                                Valor.SEIS)
                ));

        Jogador jogador =
                jogadorHumano(
                        "Jogador",
                        maoJogador);

        Jogador banca =
                new JogadorAutomatico(
                        "Banca",
                        maoBanca,
                        new EstrategiaBlackjack());

        Partida<CartaTradicional> partida =
                new PartidaDeTeste(
                        List.of(jogador, banca),
                        new Baralho<>(),
                        new RegraBlackjack(),
                        new ExecutorTeste());

        partida.iniciar(0);

        partida.encerrarPartida();

        assertTrue(
                partida.isEncerrada());

        assertSame(
                jogador,
                partida.getVencedor());
    }

    @Test
    void jogarTurnoLancaJogadaInvalidaExceptionQuandoRegraRecusaAJogada() {

        MaoDeCartas<Carta> mao =
                new MaoDeCartas<>(List.of(
                        new CartaTradicional(
                                Naipe.COPAS,
                                Valor.REI),
                        new CartaTradicional(
                                Naipe.ESPADAS,
                                Valor.DEZ),
                        new CartaTradicional(
                                Naipe.PAUS,
                                Valor.CINCO)
                ));

        /*
         * 25 pontos: mão estourada.
         */
        Jogador jogadorEstourado =
                new JogadorHumano(
                        "Jogador",
                        mao,
                        new Scanner(
                                "comprar\n"),
                        acao ->
                                new JogadaBlackjack(
                                        "COMPRAR"));

        Jogador banca =
                new JogadorAutomatico(
                        "Banca",
                        new MaoDeCartas<>(),
                        new EstrategiaBlackjack());

        Partida<CartaTradicional> partida =
                new PartidaDeTeste(
                        List.of(
                                jogadorEstourado,
                                banca),
                        new Baralho<>(),
                        new RegraBlackjack(),
                        new ExecutorTeste());

        partida.iniciar(0);

        assertThrows(
                JogadaInvalidaException.class,
                partida::jogarTurno);
    }

    @Test
    void jogarTurnoAntesDeIniciarLancaIllegalStateException() {

        Jogador jogador =
                jogadorHumano(
                        "Jogador",
                        new MaoDeCartas<>());

        Partida<CartaTradicional> partida =
                new PartidaDeTeste(
                        List.of(jogador),
                        new Baralho<>(),
                        new RegraBlackjack(),
                        new ExecutorTeste());

        assertThrows(
                IllegalStateException.class,
                partida::jogarTurno);
    }

    @Test
    void comprarCartaParaPropagaBaralhoVazioException() {

        Jogador jogador =
                jogadorHumano(
                        "Jogador",
                        new MaoDeCartas<>());

        Partida<CartaTradicional> partida =
                new PartidaDeTeste(
                        List.of(jogador),
                        new Baralho<>(),
                        new RegraBlackjack(),
                        new ExecutorTeste());

        assertThrows(
                BaralhoVazioException.class,
                () -> partida.comprarCartaPara(jogador));
    }

    /**
     * Testa se a partida impede uma distribuição parcial
     * quando o baralho não possui cartas suficientes.
     */
    @Test
    void iniciarComCartasInsuficientesNaoDistribuiParcialmente() {

        Jogador primeiro =
                jogadorHumano(
                        "Primeiro",
                        new MaoDeCartas<>());

        Jogador segundo =
                jogadorHumano(
                        "Segundo",
                        new MaoDeCartas<>());

        Baralho<CartaTradicional> baralho =
                new Baralho<>(List.of(
                        new CartaTradicional(
                                Naipe.COPAS,
                                Valor.AS)));

        Partida<CartaTradicional> partida =
                new PartidaDeTeste(
                        List.of(primeiro, segundo),
                        baralho,
                        new RegraBlackjack(),
                        new ExecutorTeste());

        assertThrows(
                br.edu.uepb.map.framework.excecoes.CartasInsuficientesException.class,
                () -> partida.iniciar(1));

        /*
         * Nenhum jogador deve receber cartas parcialmente.
         */
        assertEquals(
                0,
                primeiro.getMaoDeCartas().tamanho());

        assertEquals(
                0,
                segundo.getMaoDeCartas().tamanho());

        /*
         * A carta deve permanecer no baralho.
         */
        assertEquals(
                1,
                baralho.tamanho());

        assertFalse(
                partida.isIniciada());
    }

    /**
     * Testa se a partida impede:
     *
     * 1. uma segunda inicialização;
     * 2. que um jogador externo receba cartas.
     */
    @Test
    void rejeitaReinicioECompraParaQuemNaoParticipa() {

        Jogador participante =
                jogadorHumano(
                        "Participante",
                        new MaoDeCartas<>());

        Jogador externo =
                jogadorHumano(
                        "Externo",
                        new MaoDeCartas<>());

        Partida<CartaTradicional> partida =
                new PartidaDeTeste(
                        List.of(participante),
                        new Baralho<>(),
                        new RegraBlackjack(),
                        new ExecutorTeste());

        partida.iniciar(0);

        /*
         * A partida já foi iniciada, portanto
         * uma segunda inicialização deve falhar.
         */
        assertThrows(
                IllegalStateException.class,
                () -> partida.iniciar(0));

        /*
         * O jogador externo não pertence à partida.
         */
        assertThrows(
                IllegalArgumentException.class,
                () -> partida.comprarCartaPara(externo));
    }
}
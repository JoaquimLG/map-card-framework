package br.edu.uepb.map.jogos.blackjack;

import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.cartas.CartaTradicional;
import br.edu.uepb.map.framework.cartas.Naipe;
import br.edu.uepb.map.framework.cartas.Valor;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.jogador.JogadorHumano;
import br.edu.uepb.map.framework.jogador.MaoDeCartas;

public class RegraBlackjackTest {

    private RegraBlackjack regra;
    private Jogador jogador;

    @BeforeEach
    public void setUp() {
        regra = new RegraBlackjack();

        MaoDeCartas<Carta> mao = new MaoDeCartas<>();

        Scanner scanner = new Scanner(System.in);

        jogador = new JogadorHumano(
                "Jogador 1",
                mao,
                scanner,
                acao -> new JogadaBlackjack(acao)
        );
    }

    @Test
    public void devePermitirComprarSePontuacaoForMenorQue21() {
        jogador.receberCarta(new CartaTradicional(Naipe.COPAS, Valor.DEZ));
        jogador.receberCarta(new CartaTradicional(Naipe.ESPADAS, Valor.CINCO));

        Jogada pedirCarta = new JogadaBlackjack("COMPRAR");

        assertTrue(
                regra.jogadaValida(pedirCarta, jogador),
                "O jogador deve poder comprar enquanto estiver abaixo de 21."
        );
    }

    @Test
    public void naoDevePermitirComprarSeEstourou21() {
        jogador.receberCarta(new CartaTradicional(Naipe.COPAS, Valor.REI));
        jogador.receberCarta(new CartaTradicional(Naipe.ESPADAS, Valor.DEZ));
        jogador.receberCarta(new CartaTradicional(Naipe.PAUS, Valor.CINCO));

        Jogada pedirCarta = new JogadaBlackjack("COMPRAR");

        assertFalse(
                regra.jogadaValida(pedirCarta, jogador),
                "O jogador não deve poder comprar depois de estourar 21."
        );
    }

@Test
public void naoDevePermitirComprarCom21() {
    jogador.receberCarta(new CartaTradicional(Naipe.COPAS, Valor.DEZ));
    jogador.receberCarta(new CartaTradicional(Naipe.ESPADAS, Valor.SEIS));
    jogador.receberCarta(new CartaTradicional(Naipe.PAUS, Valor.CINCO));

    Jogada pedirCarta = new JogadaBlackjack("COMPRAR");

    assertFalse(
            regra.jogadaValida(pedirCarta, jogador),
            "O jogador não deve poder comprar quando atingir 21."
    );
}

    @Test
    public void deveIdentificarMaiorPontuacaoComoVencedor() {
        MaoDeCartas<Carta> maoJogador = new MaoDeCartas<>(
                Arrays.asList(
                        new CartaTradicional(Naipe.COPAS, Valor.DEZ),
                        new CartaTradicional(Naipe.ESPADAS, Valor.OITO)
                )
        );

        MaoDeCartas<Carta> maoBanca = new MaoDeCartas<>(
                Arrays.asList(
                        new CartaTradicional(Naipe.PAUS, Valor.DEZ),
                        new CartaTradicional(Naipe.OUROS, Valor.SEIS)
                )
        );

        Jogador jogadorComMaiorPontuacao = new JogadorHumano(
                "Jogador",
                maoJogador,
                new Scanner(System.in),
                acao -> new JogadaBlackjack(acao)
        );

        Jogador banca = new JogadorHumano(
                "Banca",
                maoBanca,
                new Scanner(System.in),
                acao -> new JogadaBlackjack(acao)
        );

        Jogador vencedor = regra.verificarVencedor(
                Arrays.asList(jogadorComMaiorPontuacao, banca)
        );

        assertSame(
                jogadorComMaiorPontuacao,
                vencedor,
                "O jogador com maior pontuação sem estourar 21 deve vencer."
        );
    }
}
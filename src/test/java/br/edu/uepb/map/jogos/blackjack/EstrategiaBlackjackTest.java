package br.edu.uepb.map.jogos.blackjack;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.cartas.CartaTradicional;
import br.edu.uepb.map.framework.cartas.Naipe;
import br.edu.uepb.map.framework.cartas.Valor;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.jogador.JogadorAutomatico;
import br.edu.uepb.map.framework.jogador.MaoDeCartas;

public class EstrategiaBlackjackTest {

    private EstrategiaBlackjack estrategia;

    @BeforeEach
    public void setUp() {
        estrategia = new EstrategiaBlackjack();
    }

    @Test
    public void deveComprarCartaSeMenorQue17() {
        MaoDeCartas<Carta> maoDaBanca = new MaoDeCartas<>(
                Arrays.asList(
                        new CartaTradicional(Naipe.COPAS, Valor.DEZ),
                        new CartaTradicional(Naipe.ESPADAS, Valor.SEIS)
                )
        );

        Jogador banca = new JogadorAutomatico(
                "Banca",
                maoDaBanca,
                estrategia
        );

        Jogada jogada = estrategia.escolherJogada(banca);

        assertTrue(jogada instanceof JogadaBlackjack);

        assertEquals(
                "COMPRAR",
                ((JogadaBlackjack) jogada).getAcao(),
                "A banca deve comprar carta quando tiver menos de 17."
        );
    }

    @Test
    public void devePararQuandoAtingir17() {
        MaoDeCartas<Carta> maoDaBanca = new MaoDeCartas<>(
                Arrays.asList(
                        new CartaTradicional(Naipe.COPAS, Valor.DEZ),
                        new CartaTradicional(Naipe.ESPADAS, Valor.SETE)
                )
        );

        Jogador banca = new JogadorAutomatico(
                "Banca",
                maoDaBanca,
                estrategia
        );

        Jogada jogada = estrategia.escolherJogada(banca);

        assertTrue(jogada instanceof JogadaBlackjack);

        assertEquals(
                "PARAR",
                ((JogadaBlackjack) jogada).getAcao(),
                "A banca deve parar quando atingir 17."
        );
    }

    @Test
    public void devePararQuandoPontuacaoForMaiorQue17() {
        MaoDeCartas<Carta> maoDaBanca = new MaoDeCartas<>(
                Arrays.asList(
                        new CartaTradicional(Naipe.COPAS, Valor.DEZ),
                        new CartaTradicional(Naipe.ESPADAS, Valor.OITO)
                )
        );

        Jogador banca = new JogadorAutomatico(
                "Banca",
                maoDaBanca,
                estrategia
        );

        Jogada jogada = estrategia.escolherJogada(banca);

        assertEquals(
                "PARAR",
                ((JogadaBlackjack) jogada).getAcao(),
                "A banca deve parar quando tiver mais de 17."
        );
    }

    @Test
    public void deveConsiderarAsComo11QuandoNaoEstoura() {
        MaoDeCartas<Carta> maoDaBanca = new MaoDeCartas<>(
                Arrays.asList(
                        new CartaTradicional(Naipe.COPAS, Valor.AS),
                        new CartaTradicional(Naipe.ESPADAS, Valor.SEIS)
                )
        );

        Jogador banca = new JogadorAutomatico(
                "Banca",
                maoDaBanca,
                estrategia
        );

        Jogada jogada = estrategia.escolherJogada(banca);

        assertEquals(
                "PARAR",
                ((JogadaBlackjack) jogada).getAcao(),
                "Ás + 6 deve ser considerado como 17."
        );
    }

    @Test
    public void deveConsiderarAsComo1QuandoNecessario() {
        MaoDeCartas<Carta> maoDaBanca = new MaoDeCartas<>(
                Arrays.asList(
                        new CartaTradicional(Naipe.COPAS, Valor.AS),
                        new CartaTradicional(Naipe.ESPADAS, Valor.DEZ),
                        new CartaTradicional(Naipe.PAUS, Valor.SEIS)
                )
        );

        Jogador banca = new JogadorAutomatico(
                "Banca",
                maoDaBanca,
                estrategia
        );

        Jogada jogada = estrategia.escolherJogada(banca);

        assertEquals(
                "PARAR",
                ((JogadaBlackjack) jogada).getAcao(),
                "Ás deve valer 1 quando necessário para evitar estouro."
        );
    }
}
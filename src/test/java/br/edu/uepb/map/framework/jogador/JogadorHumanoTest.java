package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class JogadorHumanoTest {

    private static class JogadaFake implements Jogada {
        private final String acao;

        JogadaFake(String acao) {
            this.acao = acao;
        }

        String getAcao() {
            return acao;
        }
    }

    private Scanner scannerCom(String entrada) {
        return new Scanner(new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void decidirJogadaLeLinhaDoScannerEDelegaAoMapeador() {
        Scanner scanner = scannerCom("COMPRAR\n");
        Function<String, Jogada> mapeador = JogadaFake::new;

        JogadorHumano jogador = new JogadorHumano("Jogador", new MaoDeCartas<>(), scanner, mapeador);

        Jogada jogada = jogador.decidirJogada();

        assertTrue(jogada instanceof JogadaFake);
        assertEquals("COMPRAR", ((JogadaFake) jogada).getAcao());
    }

    @Test
    void decidirJogadaLeUmaLinhaPorChamada() {
        Scanner scanner = scannerCom("COMPRAR\nPARAR\n");
        Function<String, Jogada> mapeador = JogadaFake::new;

        JogadorHumano jogador = new JogadorHumano("Jogador", new MaoDeCartas<>(), scanner, mapeador);

        Jogada primeira = jogador.decidirJogada();
        Jogada segunda = jogador.decidirJogada();

        assertEquals("COMPRAR", ((JogadaFake) primeira).getAcao());
        assertEquals("PARAR", ((JogadaFake) segunda).getAcao());
    }

    @Test
    void repassaAEntradaExatamenteComoDigitadaParaOMapeador() {
        Scanner scanner = scannerCom("  comprar  \n");
        Function<String, Jogada> mapeador = JogadaFake::new;

        JogadorHumano jogador = new JogadorHumano("Jogador", new MaoDeCartas<>(), scanner, mapeador);

        Jogada jogada = jogador.decidirJogada();

        assertEquals("  comprar  ", ((JogadaFake) jogada).getAcao());
    }

    @Test
    void herdaComportamentoDeJogador() {
        MaoDeCartas<Carta> mao = new MaoDeCartas<>();
        JogadorHumano jogador = new JogadorHumano("Jogador", mao, scannerCom(""), JogadaFake::new);

        assertEquals("Jogador", jogador.getNome());
        assertTrue(jogador.isAtivo());
        jogador.desativar();
        assertFalse(jogador.isAtivo());
    }
}
package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.estrategia.Estrategia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JogadorAutomaticoTest {

    private static class JogadaFake implements Jogada {
    }

    private static class EstrategiaFake implements Estrategia {
        private final Jogada jogadaParaDevolver;
        private Jogador jogadorRecebido;
        private int chamadas;

        EstrategiaFake(Jogada jogadaParaDevolver) {
            this.jogadaParaDevolver = jogadaParaDevolver;
        }

        @Override
        public Jogada escolherJogada(Jogador jogador) {
            this.jogadorRecebido = jogador;
            this.chamadas++;
            return jogadaParaDevolver;
        }
    }

    @Test
    void decidirJogadaDelegaParaAEstrategiaERepassaOProprioJogador() {
        Jogada jogadaEsperada = new JogadaFake();
        EstrategiaFake estrategia = new EstrategiaFake(jogadaEsperada);
        JogadorAutomatico banca = new JogadorAutomatico("Banca", new MaoDeCartas<>(), estrategia);

        Jogada jogada = banca.decidirJogada();

        assertSame(jogadaEsperada, jogada);
        assertSame(banca, estrategia.jogadorRecebido);
    }

    @Test
    void decidirJogadaChamaAEstrategiaACadaChamada() {
        EstrategiaFake estrategia = new EstrategiaFake(new JogadaFake());
        JogadorAutomatico banca = new JogadorAutomatico("Banca", new MaoDeCartas<>(), estrategia);

        banca.decidirJogada();
        banca.decidirJogada();
        banca.decidirJogada();

        assertEquals(3, estrategia.chamadas);
    }

    @Test
    void herdaComportamentoDeJogador() {
        MaoDeCartas<Carta> mao = new MaoDeCartas<>();
        JogadorAutomatico banca = new JogadorAutomatico("Banca", mao, new EstrategiaFake(new JogadaFake()));

        assertEquals("Banca", banca.getNome());
        assertTrue(banca.isAtivo());
        banca.desativar();
        assertFalse(banca.isAtivo());
    }

    @Test
    void rejeitaEstrategiaNulaEJogadaNulaProduzidaPelaEstrategia() {
        assertThrows(NullPointerException.class,
                () -> new JogadorAutomatico("Banca", new MaoDeCartas<>(), null));

        JogadorAutomatico banca = new JogadorAutomatico(
                "Banca", new MaoDeCartas<>(), new EstrategiaFake(null));
        assertThrows(NullPointerException.class, banca::decidirJogada);
    }
}

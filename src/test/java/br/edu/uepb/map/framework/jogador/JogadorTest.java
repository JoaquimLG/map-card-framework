package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JogadorTest {

    private static class JogadorFake extends Jogador {
        JogadorFake(String nome, MaoDeCartas<Carta> maoDeCartas) {
            super(nome, maoDeCartas);
        }

        @Override
        public Jogada decidirJogada() {
            return null;
        }
    }

    private record CartaDeMemoria(String descricao) implements Carta {
        @Override
        public String getDescricao() {
            return descricao;
        }
    }

    @Test
    void armazenaNomeEMaoRecebidosNoConstrutor() {
        MaoDeCartas<Carta> mao = new MaoDeCartas<>();
        Jogador jogador = new JogadorFake("Jogador 1", mao);

        assertEquals("Jogador 1", jogador.getNome());
        assertSame(mao, jogador.getMaoDeCartas());
    }

    @Test
    void comecaAtivo() {
        Jogador jogador = new JogadorFake("Jogador 1", new MaoDeCartas<>());

        assertTrue(jogador.isAtivo());
    }

    @Test
    void desativarTornaJogadorInativo() {
        Jogador jogador = new JogadorFake("Jogador 1", new MaoDeCartas<>());

        jogador.desativar();

        assertFalse(jogador.isAtivo());
    }

    @Test
    void desativarChamadoMaisDeUmaVezMantemInativo() {
        Jogador jogador = new JogadorFake("Jogador 1", new MaoDeCartas<>());

        jogador.desativar();
        jogador.desativar();

        assertFalse(jogador.isAtivo());
    }

    @Test
    void receberCartaAdicionaNaMaoDoJogador() {
        MaoDeCartas<Carta> mao = new MaoDeCartas<>();
        Jogador jogador = new JogadorFake("Jogador 1", mao);
        Carta carta = new CartaDeMemoria("carta");

        jogador.receberCarta(carta);

        assertEquals(List.of(carta), jogador.getMaoDeCartas().listarCartas());
        assertEquals(1, jogador.getMaoDeCartas().tamanho());
    }

    @Test
    void rejeitaNomeNuloVazioEMaoNula() {
        assertThrows(NullPointerException.class,
                () -> new JogadorFake(null, new MaoDeCartas<>()));
        assertThrows(IllegalArgumentException.class,
                () -> new JogadorFake("   ", new MaoDeCartas<>()));
        assertThrows(NullPointerException.class,
                () -> new JogadorFake("Jogador", null));
    }
}

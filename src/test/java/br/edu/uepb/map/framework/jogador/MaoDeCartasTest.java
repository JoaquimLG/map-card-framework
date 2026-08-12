package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaoDeCartasTest {

    @Test
    void criaMaoVazia() {
        MaoDeCartas<CartaDeMemoria> mao = new MaoDeCartas<>();

        assertTrue(mao.estaVazia());
        assertEquals(0, mao.tamanho());
        assertEquals(List.of(), mao.listarCartas());
    }

    @Test
    void recebeCartasPreservandoOrdemEDuplicatas() {
        CartaDeMemoria repetida = new CartaDeMemoria("repetida");
        CartaDeMemoria outra = new CartaDeMemoria("outra");
        MaoDeCartas<CartaDeMemoria> mao = new MaoDeCartas<>();

        mao.receberCarta(repetida);
        mao.receberCarta(outra);
        mao.receberCarta(repetida);

        assertEquals(List.of(repetida, outra, repetida), mao.listarCartas());
        assertEquals(3, mao.tamanho());
        assertFalse(mao.estaVazia());
    }

    @Test
    void criaMaoComCopiaDefensivaDaColecao() {
        CartaDeMemoria primeira = new CartaDeMemoria("primeira");
        CartaDeMemoria segunda = new CartaDeMemoria("segunda");
        List<CartaDeMemoria> cartas = new ArrayList<>(List.of(primeira, segunda));

        MaoDeCartas<CartaDeMemoria> mao = new MaoDeCartas<>(cartas);
        cartas.clear();

        assertEquals(List.of(primeira, segunda), mao.listarCartas());
    }

    @Test
    void jogaSomenteAPrimeiraOcorrenciaIgual() {
        CartaDeMemoria repetida = new CartaDeMemoria("repetida");
        CartaDeMemoria outra = new CartaDeMemoria("outra");
        MaoDeCartas<CartaDeMemoria> mao = new MaoDeCartas<>(
                List.of(repetida, outra, new CartaDeMemoria("repetida")));

        boolean jogou = mao.jogarCarta(repetida);

        assertTrue(jogou);
        assertEquals(List.of(outra, repetida), mao.listarCartas());
        assertEquals(2, mao.tamanho());
    }

    @Test
    void jogarCartaAusenteRetornaFalsoSemAlterarAMao() {
        CartaDeMemoria existente = new CartaDeMemoria("existente");
        MaoDeCartas<CartaDeMemoria> mao = new MaoDeCartas<>(List.of(existente));

        boolean jogou = mao.jogarCarta(new CartaDeMemoria("ausente"));

        assertFalse(jogou);
        assertEquals(List.of(existente), mao.listarCartas());
    }

    @Test
    void listagemNaoPermiteModificarAListaInterna() {
        CartaDeMemoria primeira = new CartaDeMemoria("primeira");
        CartaDeMemoria segunda = new CartaDeMemoria("segunda");
        MaoDeCartas<CartaDeMemoria> mao = new MaoDeCartas<>(List.of(primeira));
        List<CartaDeMemoria> consulta = mao.listarCartas();

        assertThrows(UnsupportedOperationException.class, () -> consulta.add(segunda));
        mao.receberCarta(segunda);

        assertEquals(List.of(primeira), consulta);
        assertEquals(List.of(primeira, segunda), mao.listarCartas());
    }

    @Test
    void rejeitaNulosSemCorromperOEstado() {
        CartaDeMemoria existente = new CartaDeMemoria("existente");
        MaoDeCartas<CartaDeMemoria> mao = new MaoDeCartas<>(List.of(existente));

        assertThrows(NullPointerException.class, () -> new MaoDeCartas<CartaDeMemoria>(null));
        assertThrows(NullPointerException.class,
                () -> new MaoDeCartas<>(java.util.Arrays.asList(existente, null)));
        assertThrows(NullPointerException.class, () -> mao.receberCarta(null));
        assertThrows(NullPointerException.class, () -> mao.jogarCarta(null));

        assertEquals(List.of(existente), mao.listarCartas());
    }

    private record CartaDeMemoria(String descricao) implements Carta {
        @Override
        public String getDescricao() {
            return descricao;
        }
    }
}

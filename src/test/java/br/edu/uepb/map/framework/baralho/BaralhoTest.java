package br.edu.uepb.map.framework.baralho;

import br.edu.uepb.map.framework.cartas.Carta;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaralhoTest {

    @Test
    void baralhoVazioInformaQuantidadeZeroEEstadoVazio() {
        Baralho<CartaDeMemoria> baralho = new Baralho<>();

        assertEquals(0, baralho.getQuantidade());
        assertTrue(baralho.estaVazio());
        assertEquals(List.of(), baralho.getCartas());
    }

    @Test
    void criaBaralhoComCopiaDaColecaoPreservandoOrdemEDuplicatas() {
        CartaDeMemoria primeira = new CartaDeMemoria("primeira");
        CartaDeMemoria segunda = new CartaDeMemoria("segunda");
        List<CartaDeMemoria> composicao = new ArrayList<>(List.of(primeira, segunda, primeira));

        Baralho<CartaDeMemoria> baralho = new Baralho<>(composicao);
        composicao.clear();

        assertEquals(List.of(primeira, segunda, primeira), baralho.getCartas());
        assertEquals(3, baralho.getQuantidade());
    }

    @Test
    void adicionaCartaEColecaoMantendoOrdemEDuplicatas() {
        CartaDeMemoria primeira = new CartaDeMemoria("primeira");
        CartaDeMemoria segunda = new CartaDeMemoria("segunda");
        Baralho<CartaDeMemoria> baralho = new Baralho<>();

        baralho.adicionar(primeira);
        baralho.adicionar(List.of(segunda, primeira));

        assertEquals(List.of(primeira, segunda, primeira), baralho.getCartas());
        assertEquals(3, baralho.getQuantidade());
        assertFalse(baralho.estaVazio());
    }

    @Test
    void rejeitaNulosSemCorromperOEstado() {
        CartaDeMemoria existente = new CartaDeMemoria("existente");
        CartaDeMemoria nova = new CartaDeMemoria("nova");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(existente));

        assertThrows(NullPointerException.class, () -> new Baralho<CartaDeMemoria>(null));
        assertThrows(NullPointerException.class, () -> new Baralho<>(java.util.Arrays.asList(existente, null)));
        assertThrows(NullPointerException.class, () -> baralho.adicionar((CartaDeMemoria) null));
        assertThrows(NullPointerException.class, () -> baralho.adicionar((List<CartaDeMemoria>) null));
        assertThrows(NullPointerException.class,
                () -> baralho.adicionar(java.util.Arrays.asList(nova, null)));

        assertEquals(List.of(existente), baralho.getCartas());
    }

    @Test
    void consultaCartasNaoPermiteModificarOEstadoInterno() {
        CartaDeMemoria primeira = new CartaDeMemoria("primeira");
        CartaDeMemoria segunda = new CartaDeMemoria("segunda");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(primeira));
        List<CartaDeMemoria> consulta = baralho.getCartas();

        assertThrows(UnsupportedOperationException.class, () -> consulta.add(segunda));
        baralho.adicionar(segunda);

        assertEquals(List.of(primeira), consulta);
        assertEquals(List.of(primeira, segunda), baralho.getCartas());
    }

    private record CartaDeMemoria(String descricao) implements Carta {
        @Override
        public String getDescricao() {
            return descricao;
        }
    }
}

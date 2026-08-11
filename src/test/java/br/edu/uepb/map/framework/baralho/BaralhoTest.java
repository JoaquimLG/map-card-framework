package br.edu.uepb.map.framework.baralho;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.excecoes.BaralhoVazioException;
import br.edu.uepb.map.framework.excecoes.CartasInsuficientesException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaralhoTest {

    @Test
    void embaralhaDeModoReproduzivelComFonteControlada() {
        CartaDeMemoria primeira = new CartaDeMemoria("primeira");
        CartaDeMemoria segunda = new CartaDeMemoria("segunda");
        CartaDeMemoria terceira = new CartaDeMemoria("terceira");
        CartaDeMemoria quarta = new CartaDeMemoria("quarta");
        Baralho<CartaDeMemoria> primeiro = new Baralho<>(List.of(primeira, segunda, terceira, quarta));
        Baralho<CartaDeMemoria> segundo = new Baralho<>(List.of(primeira, segunda, terceira, quarta));

        primeiro.embaralhar(new Random(7));
        segundo.embaralhar(new Random(7));

        assertEquals(List.of(primeira, segunda, quarta, terceira), primeiro.getCartas());
        assertEquals(primeiro.getCartas(), segundo.getCartas());
    }

    @Test
    void embaralhamentoComumPreservaQuantidadeEOcorrenciasInclusiveDuplicatas() {
        CartaDeMemoria repetida = new CartaDeMemoria("repetida");
        CartaDeMemoria outra = new CartaDeMemoria("outra");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(repetida, outra, repetida));

        baralho.embaralhar();

        assertEquals(3, baralho.getQuantidade());
        assertEquals(2, Collections.frequency(baralho.getCartas(), repetida));
        assertEquals(1, Collections.frequency(baralho.getCartas(), outra));
    }

    @Test
    void rejeitaFonteDeAleatoriedadeNulaSemAlterarOBaralho() {
        CartaDeMemoria carta = new CartaDeMemoria("carta");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(carta));

        assertThrows(NullPointerException.class, () -> baralho.embaralhar(null));

        assertEquals(List.of(carta), baralho.getCartas());
    }

    @Test
    void embaralhaBaralhosVazioEComUmaCartaSemAlteracaoIndevida() {
        CartaDeMemoria unica = new CartaDeMemoria("unica");
        Baralho<CartaDeMemoria> vazio = new Baralho<>();
        Baralho<CartaDeMemoria> unitario = new Baralho<>(List.of(unica));

        vazio.embaralhar(new Random(7));
        unitario.embaralhar(new Random(7));

        assertEquals(List.of(), vazio.getCartas());
        assertEquals(List.of(unica), unitario.getCartas());
    }

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
        assertThrows(NullPointerException.class, () -> baralho.remover(null));

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

    @Test
    void compraCartaDoTopoEAtualizaOEstado() {
        CartaDeMemoria primeira = new CartaDeMemoria("primeira");
        CartaDeMemoria topo = new CartaDeMemoria("topo");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(primeira, topo));

        CartaDeMemoria comprada = baralho.comprar();

        assertEquals(topo, comprada);
        assertEquals(List.of(primeira), baralho.getCartas());
        assertEquals(1, baralho.getQuantidade());
        assertFalse(baralho.estaVazio());
    }

    @Test
    void comprarUltimaCartaDeixaBaralhoVazio() {
        CartaDeMemoria unica = new CartaDeMemoria("unica");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(unica));

        assertEquals(unica, baralho.comprar());
        assertEquals(0, baralho.getQuantidade());
        assertTrue(baralho.estaVazio());
    }

    @Test
    void comprarDeBaralhoVazioLancaExcecaoEspecifica() {
        Baralho<CartaDeMemoria> baralho = new Baralho<>();

        assertThrows(BaralhoVazioException.class, baralho::comprar);

        assertTrue(baralho.estaVazio());
        assertEquals(0, baralho.getQuantidade());
    }

    @Test
    void compraVariasCartasNaOrdemDoTopo() {
        CartaDeMemoria primeira = new CartaDeMemoria("primeira");
        CartaDeMemoria segunda = new CartaDeMemoria("segunda");
        CartaDeMemoria topo = new CartaDeMemoria("topo");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(primeira, segunda, topo));

        List<CartaDeMemoria> compradas = baralho.comprar(2);

        assertEquals(List.of(topo, segunda), compradas);
        assertEquals(List.of(primeira), baralho.getCartas());
        assertEquals(1, baralho.getQuantidade());
    }

    @Test
    void compraInsuficienteLancaExcecaoEspecificaSemAlterarOBaralho() {
        CartaDeMemoria primeira = new CartaDeMemoria("primeira");
        CartaDeMemoria topo = new CartaDeMemoria("topo");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(primeira, topo));

        assertThrows(CartasInsuficientesException.class, () -> baralho.comprar(3));

        assertEquals(List.of(primeira, topo), baralho.getCartas());
        assertEquals(2, baralho.getQuantidade());
        assertFalse(baralho.estaVazio());
    }

    @Test
    void rejeitaQuantidadeDeCompraNaoPositivaSemAlterarOBaralho() {
        CartaDeMemoria carta = new CartaDeMemoria("carta");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(carta));

        assertThrows(IllegalArgumentException.class, () -> baralho.comprar(0));
        assertThrows(IllegalArgumentException.class, () -> baralho.comprar(-1));

        assertEquals(List.of(carta), baralho.getCartas());
        assertEquals(1, baralho.getQuantidade());
    }

    @Test
    void removeSomenteAPrimeiraOcorrenciaIgual() {
        CartaDeMemoria repetida = new CartaDeMemoria("repetida");
        CartaDeMemoria outra = new CartaDeMemoria("outra");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(repetida, outra,
                new CartaDeMemoria("repetida")));

        boolean removeu = baralho.remover(repetida);

        assertTrue(removeu);
        assertEquals(List.of(outra, repetida), baralho.getCartas());
        assertEquals(2, baralho.getQuantidade());
    }

    @Test
    void removerCartaAusenteRetornaFalsoSemAlterarOBaralho() {
        CartaDeMemoria existente = new CartaDeMemoria("existente");
        Baralho<CartaDeMemoria> baralho = new Baralho<>(List.of(existente));

        boolean removeu = baralho.remover(new CartaDeMemoria("ausente"));

        assertFalse(removeu);
        assertEquals(List.of(existente), baralho.getCartas());
        assertEquals(1, baralho.getQuantidade());
    }

    private record CartaDeMemoria(String descricao) implements Carta {
        @Override
        public String getDescricao() {
            return descricao;
        }
    }
}

package br.edu.uepb.map.framework.cartas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartaTest {

    @Test
    void permiteImplementarCartaSemNaipeOuValorTradicional() {
        Carta carta = new CartaDeMemoria("Carta escolhida");

        assertEquals("Carta escolhida", carta.getDescricao());
    }

    @Test
    void cartaTradicionalExpoeNaipeValorEDescricao() {
        CartaTradicional carta = new CartaTradicional(Naipe.COPAS, Valor.AS);

        assertEquals(Naipe.COPAS, carta.getNaipe());
        assertEquals(Valor.AS, carta.getValor());
        assertEquals("Ás de Copas", carta.getDescricao());
        assertEquals("Ás de Copas", carta.toString());
    }

    @Test
    void disponibilizaTodosOsNaipesTradicionaisSemOrdemDeForca() {
        assertArrayEquals(
                new Naipe[]{Naipe.COPAS, Naipe.OUROS, Naipe.ESPADAS, Naipe.PAUS},
                Naipe.values()
        );
    }

    @Test
    void disponibilizaValoresDeAsAReiSemPontuacao() {
        assertArrayEquals(
                new Valor[]{
                        Valor.AS, Valor.DOIS, Valor.TRES, Valor.QUATRO, Valor.CINCO,
                        Valor.SEIS, Valor.SETE, Valor.OITO, Valor.NOVE, Valor.DEZ,
                        Valor.VALETE, Valor.DAMA, Valor.REI
                },
                Valor.values()
        );
    }

    @Test
    void cartaTradicionalPossuiIgualdadePorNaipeEValor() {
        CartaTradicional asDeCopas = new CartaTradicional(Naipe.COPAS, Valor.AS);
        CartaTradicional outroAsDeCopas = new CartaTradicional(Naipe.COPAS, Valor.AS);
        CartaTradicional asDeOuros = new CartaTradicional(Naipe.OUROS, Valor.AS);
        CartaTradicional reiDeCopas = new CartaTradicional(Naipe.COPAS, Valor.REI);

        assertEquals(asDeCopas, outroAsDeCopas);
        assertEquals(asDeCopas.hashCode(), outroAsDeCopas.hashCode());
        assertNotEquals(asDeCopas, asDeOuros);
        assertNotEquals(asDeCopas, reiDeCopas);
    }

    @Test
    void cartaTradicionalRejeitaNaipeOuValorNulo() {
        assertThrows(NullPointerException.class, () -> new CartaTradicional(null, Valor.AS));
        assertThrows(NullPointerException.class, () -> new CartaTradicional(Naipe.COPAS, null));
    }

    private record CartaDeMemoria(String descricao) implements Carta {
        @Override
        public String getDescricao() {
            return descricao;
        }
    }
}

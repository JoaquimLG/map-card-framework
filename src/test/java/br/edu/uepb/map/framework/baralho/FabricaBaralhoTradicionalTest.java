package br.edu.uepb.map.framework.baralho;

import br.edu.uepb.map.framework.cartas.CartaTradicional;
import br.edu.uepb.map.framework.cartas.Naipe;
import br.edu.uepb.map.framework.cartas.Valor;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FabricaBaralhoTradicionalTest {

    @Test
    void criaComposicaoCompletaComUmaCartaDeCadaNaipeEValor() {
        FabricaBaralho<CartaTradicional> fabrica = new FabricaBaralhoTradicional();

        Baralho<CartaTradicional> baralho = fabrica.criar();

        assertEquals(52, baralho.getQuantidade());
        for (Naipe naipe : Naipe.values()) {
            for (Valor valor : Valor.values()) {
                assertEquals(1, Collections.frequency(
                        baralho.getCartas(), new CartaTradicional(naipe, valor)));
            }
        }
    }
}

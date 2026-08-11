package br.edu.uepb.map.jogos.truco;

import br.edu.uepb.map.framework.baralho.Baralho;
import br.edu.uepb.map.framework.baralho.FabricaBaralho;
import br.edu.uepb.map.framework.cartas.CartaTradicional;
import br.edu.uepb.map.framework.cartas.Naipe;
import br.edu.uepb.map.framework.cartas.Valor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrucoTest {

    @Test
    void consomeFabricaPublicaEPreservaOperacoesDoBaralho() {
        CartaTradicional repetida = new CartaTradicional(Naipe.COPAS, Valor.AS);
        FabricaBaralho<CartaTradicional> fabrica =
                () -> new Baralho<>(List.of(repetida, repetida));
        Truco truco = new Truco(fabrica);

        Baralho<CartaTradicional> baralho = truco.criarBaralho();
        baralho.embaralhar(new Random(7));

        assertEquals(2, baralho.getQuantidade());
        assertEquals(repetida, baralho.comprar());
        assertEquals(1, baralho.getQuantidade());
    }
}

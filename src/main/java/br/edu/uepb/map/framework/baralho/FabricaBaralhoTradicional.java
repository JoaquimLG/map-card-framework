package br.edu.uepb.map.framework.baralho;

import br.edu.uepb.map.framework.cartas.CartaTradicional;
import br.edu.uepb.map.framework.cartas.Naipe;
import br.edu.uepb.map.framework.cartas.Valor;

/**
 * Fábrica da composição tradicional de 52 cartas,
 * sem coringas ou regras de jogo.
 */
public final class FabricaBaralhoTradicional
        extends FabricaBaralho<CartaTradicional> {

    /**
     * Cria uma fábrica sem estado para baralhos tradicionais.
     */
    public FabricaBaralhoTradicional() {
    }

    /**
     * Adiciona ao baralho uma carta para cada combinação
     * dos quatro naipes e treze valores tradicionais.
     *
     * @param baralho baralho que receberá as 52 cartas tradicionais
     * @throws NullPointerException se o baralho for {@code null}
     */
    @Override
    protected void adicionarCartas(
            Baralho<CartaTradicional> baralho) {

        for (Naipe naipe : Naipe.values()) {
            for (Valor valor : Valor.values()) {
                baralho.adicionar(
                        new CartaTradicional(naipe, valor)
                );
            }
        }
    }
}

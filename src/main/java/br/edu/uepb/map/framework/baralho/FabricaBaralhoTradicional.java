package br.edu.uepb.map.framework.baralho;

import br.edu.uepb.map.framework.cartas.CartaTradicional;
import br.edu.uepb.map.framework.cartas.Naipe;
import br.edu.uepb.map.framework.cartas.Valor;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabrica a composicao tradicional de 52 cartas, sem coringas ou regras de jogo.
 */
public final class FabricaBaralhoTradicional implements FabricaBaralho<CartaTradicional> {

    /**
     * Cria uma fabrica sem estado para baralhos tradicionais.
     */
    public FabricaBaralhoTradicional() {
    }

    /**
     * Cria um baralho independente com uma carta para cada combinacao dos
     * quatro naipes e treze valores tradicionais.
     *
     * @return novo baralho tradicional com 52 cartas
     */
    @Override
    public Baralho<CartaTradicional> criar() {
        List<CartaTradicional> cartas = new ArrayList<>();
        for (Naipe naipe : Naipe.values()) {
            for (Valor valor : Valor.values()) {
                cartas.add(new CartaTradicional(naipe, valor));
            }
        }
        return new Baralho<>(cartas);
    }
}

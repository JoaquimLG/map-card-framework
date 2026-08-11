package br.edu.uepb.map.framework.baralho;

import br.edu.uepb.map.framework.cartas.Carta;

/**
 * Ponto de extensao para composicoes completas de baralhos.
 *
 * @param <T> subtipo de carta produzido pela fabrica
 */
@FunctionalInterface
public interface FabricaBaralho<T extends Carta> {

    /**
     * Cria um novo baralho com a composicao definida pela fabrica.
     *
     * <p>Cada chamada deve devolver um baralho independente e mutavel pelas
     * operacoes publicas de {@link Baralho}.</p>
     *
     * @return novo baralho completo, nunca {@code null}
     */
    Baralho<T> criar();
}

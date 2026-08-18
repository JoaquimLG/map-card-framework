package br.edu.uepb.map.framework.baralho;

import br.edu.uepb.map.framework.cartas.Carta;

/**
 * Fábrica abstrata responsável pela criação de baralhos.
 *
 * @param <T> subtipo de carta produzido pela fábrica
 */
public abstract class FabricaBaralho<T extends Carta> {

    /**
     * Cria um novo baralho.
     *
     * <p>O algoritmo de criação é definido nesta classe,
     * enquanto a composição das cartas é definida pelas subclasses.</p>
     *
     * @return novo baralho completo, nunca {@code null}
     */
    public final Baralho<T> criar() {
        Baralho<T> baralho = new Baralho<>();
        adicionarCartas(baralho);
        return baralho;
    }

    /**
     * Define as cartas que devem compor o baralho.
     *
     * <p>Este é o ponto de extensão utilizado pelas subclasses
     * para definir a composição específica do baralho.</p>
     *
     * @param baralho baralho que receberá as cartas
     */
    protected abstract void adicionarCartas(Baralho<T> baralho);
}
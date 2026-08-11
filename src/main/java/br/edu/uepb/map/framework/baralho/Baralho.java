package br.edu.uepb.map.framework.baralho;

import br.edu.uepb.map.framework.cartas.Carta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Colecao generica de cartas que preserva a ordem e aceita duplicatas.
 *
 * @param <T> tipo de carta armazenado
 */
public class Baralho<T extends Carta> {
    private final List<T> cartas = new ArrayList<>();

    /**
     * Cria um baralho vazio.
     */
    public Baralho() {
    }

    /**
     * Cria um baralho com uma copia da composicao informada.
     *
     * @param cartas cartas iniciais, na ordem desejada
     * @throws NullPointerException se a colecao ou algum de seus elementos for nulo
     */
    public Baralho(Collection<? extends T> cartas) {
        this.cartas.addAll(copiaValidada(cartas));
    }

    /**
     * Adiciona uma carta ao final da ordem atual.
     *
     * @param carta carta a adicionar
     * @throws NullPointerException se a carta for nula
     */
    public void adicionar(T carta) {
        cartas.add(Objects.requireNonNull(carta, "carta nao pode ser nula"));
    }

    /**
     * Adiciona todas as cartas ao final da ordem atual.
     *
     * @param cartas cartas a adicionar, na ordem fornecida
     * @throws NullPointerException se a colecao ou algum de seus elementos for nulo
     */
    public void adicionar(Collection<? extends T> cartas) {
        this.cartas.addAll(copiaValidada(cartas));
    }

    private static <T> List<T> copiaValidada(Collection<? extends T> cartas) {
        Objects.requireNonNull(cartas, "cartas nao pode ser nulo");
        return List.copyOf(cartas);
    }

    /**
     * Retorna a quantidade atual de cartas sem modificar o baralho.
     *
     * @return quantidade de cartas armazenadas
     */
    public int getQuantidade() {
        return cartas.size();
    }

    /**
     * Informa se o baralho nao possui cartas, sem modificar seu estado.
     *
     * @return {@code true} quando nao ha cartas
     */
    public boolean estaVazio() {
        return cartas.isEmpty();
    }

    /**
     * Retorna as cartas na ordem atual sem permitir alteracao da colecao interna.
     *
     * @return copia imutavel das cartas
     */
    public List<T> getCartas() {
        return List.copyOf(cartas);
    }
}

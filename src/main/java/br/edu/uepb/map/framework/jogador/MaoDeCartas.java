package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Colecao generica das cartas mantidas por um jogador.
 *
 * <p>A mao preserva a ordem de recebimento e aceita cartas repetidas, mas nao
 * expoe sua colecao interna para modificacao externa.</p>
 *
 * @param <T> tipo de carta mantido na mao
 */
public class MaoDeCartas<T extends Carta> {
    private final List<T> cartas = new ArrayList<>();

    /**
     * Cria uma mao vazia.
     */
    public MaoDeCartas() {
    }

    /**
     * Cria uma mao com uma copia das cartas informadas.
     *
     * @param cartas cartas iniciais, na ordem desejada
     * @throws NullPointerException se a colecao ou algum elemento for nulo
     */
    public MaoDeCartas(Collection<? extends T> cartas) {
        this.cartas.addAll(copiaValidada(cartas));
    }

    /**
     * Adiciona uma carta ao final da mao.
     *
     * @param carta carta recebida
     * @throws NullPointerException se a carta for nula
     */
    public void receberCarta(T carta) {
        cartas.add(Objects.requireNonNull(carta, "carta nao pode ser nula"));
    }

    /**
     * Remove da mao a primeira ocorrencia igual a carta informada.
     *
     * @param carta carta jogada
     * @return {@code true} se a carta estava na mao e foi removida
     * @throws NullPointerException se a carta for nula
     */
    public boolean jogarCarta(T carta) {
        return cartas.remove(Objects.requireNonNull(carta, "carta nao pode ser nula"));
    }

    /**
     * Lista as cartas na ordem de recebimento sem expor a colecao interna.
     *
     * @return copia imutavel das cartas da mao
     */
    public List<T> listarCartas() {
        return List.copyOf(cartas);
    }

    /**
     * Retorna a quantidade atual de cartas na mao.
     *
     * @return quantidade de cartas
     */
    public int tamanho() {
        return cartas.size();
    }

    /**
     * Informa se a mao nao possui cartas.
     *
     * @return {@code true} quando a mao esta vazia
     */
    public boolean estaVazia() {
        return cartas.isEmpty();
    }

    private static <T> List<T> copiaValidada(Collection<? extends T> cartas) {
        Objects.requireNonNull(cartas, "cartas nao pode ser nulo");
        return List.copyOf(cartas);
    }
}

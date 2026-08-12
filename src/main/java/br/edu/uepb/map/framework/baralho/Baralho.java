package br.edu.uepb.map.framework.baralho;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.excecoes.BaralhoVazioException;
import br.edu.uepb.map.framework.excecoes.CartasInsuficientesException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

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
    public int tamanho() {
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

    /**
     * Remove e retorna a carta no topo do baralho.
     *
     * @return carta que ocupava o topo
     * @throws BaralhoVazioException se o baralho estiver vazio
     */
    public T comprarCarta() {
        if (cartas.isEmpty()) {
            throw new BaralhoVazioException();
        }
        return cartas.remove(cartas.size() - 1);
    }

    /**
     * Remove e retorna cartas consecutivas do topo, na ordem em que sao compradas.
     *
     * @param quantidade numero de cartas a comprar
     * @return cartas compradas, com a antiga carta do topo na primeira posicao
     * @throws IllegalArgumentException se a quantidade nao for positiva
     * @throws CartasInsuficientesException se a quantidade exceder as cartas disponiveis;
     *         nesse caso, o baralho nao e alterado
     */
    public List<T> comprarCartas(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("quantidade deve ser positiva");
        }
        if (quantidade > cartas.size()) {
            throw new CartasInsuficientesException(quantidade, cartas.size());
        }
        List<T> compradas = new ArrayList<>();
        for (int indice = 0; indice < quantidade; indice++) {
            compradas.add(comprarCarta());
        }
        return List.copyOf(compradas);
    }

    /**
     * Remove somente a primeira ocorrencia igual a carta informada.
     *
     * @param carta carta cuja primeira ocorrencia deve ser removida
     * @return {@code true} se uma ocorrencia foi removida; {@code false} se estava ausente
     * @throws NullPointerException se a carta for nula
     */
    public boolean remover(T carta) {
        return cartas.remove(Objects.requireNonNull(carta, "carta nao pode ser nula"));
    }

    /**
     * Embaralha as cartas usando a fonte de aleatoriedade do ambiente.
     */
    public void embaralhar() {
        embaralhar(ThreadLocalRandom.current());
    }

    /**
     * Embaralha as cartas usando a fonte de aleatoriedade informada.
     *
     * @param aleatoriedade fonte usada para escolher as trocas
     * @throws NullPointerException se a fonte de aleatoriedade for nula
     */
    public void embaralhar(RandomGenerator aleatoriedade) {
        Objects.requireNonNull(aleatoriedade, "aleatoriedade nao pode ser nula");
        for (int indice = cartas.size(); indice > 1; indice--) {
            int destino = aleatoriedade.nextInt(indice);
            T carta = cartas.get(indice - 1);
            cartas.set(indice - 1, cartas.get(destino));
            cartas.set(destino, carta);
        }
    }
}

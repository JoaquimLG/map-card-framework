package br.edu.uepb.map.framework.cartas;

import java.util.Objects;

/**
 * Carta tradicional imutavel identificada por naipe e valor.
 *
 * <p>Duas cartas tradicionais sao iguais quando possuem o mesmo naipe e o
 * mesmo valor, produzindo codigos de dispersao compativeis.</p>
 */
public final class CartaTradicional implements Carta {
    private final Naipe naipe;
    private final Valor valor;

    /**
     * Cria uma carta tradicional.
     *
     * @param naipe naipe da carta, nao pode ser {@code null}
     * @param valor valor da carta, nao pode ser {@code null}
     * @throws NullPointerException se o naipe ou o valor for {@code null}
     */
    public CartaTradicional(Naipe naipe, Valor valor) {
        this.naipe = Objects.requireNonNull(naipe, "naipe nao pode ser nulo");
        this.valor = Objects.requireNonNull(valor, "valor nao pode ser nulo");
    }

    /**
     * Retorna o naipe desta carta.
     *
     * @return o naipe, nunca {@code null}
     */
    public Naipe getNaipe() {
        return naipe;
    }

    /**
     * Retorna o valor desta carta.
     *
     * @return o valor, nunca {@code null}
     */
    public Valor getValor() {
        return valor;
    }

    /**
     * Retorna a descricao da carta no formato "Valor de Naipe".
     *
     * @return uma descricao adequada para exibicao em console
     */
    @Override
    public String getDescricao() {
        return valor.getDescricao() + " de " + naipe.getDescricao();
    }

    /**
     * Compara esta carta por naipe e valor.
     *
     * @param objeto objeto a ser comparado, podendo ser {@code null}
     * @return {@code true} quando o objeto for uma carta tradicional com os
     *         mesmos naipe e valor
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof CartaTradicional outraCarta)) {
            return false;
        }
        return naipe == outraCarta.naipe && valor == outraCarta.valor;
    }

    /**
     * Retorna um codigo de dispersao derivado do naipe e do valor.
     *
     * @return codigo compativel com a igualdade por naipe e valor
     */
    @Override
    public int hashCode() {
        return Objects.hash(naipe, valor);
    }

    /**
     * Retorna a mesma descricao publica usada para exibicao em console.
     *
     * @return a descricao da carta
     */
    @Override
    public String toString() {
        return getDescricao();
    }
}

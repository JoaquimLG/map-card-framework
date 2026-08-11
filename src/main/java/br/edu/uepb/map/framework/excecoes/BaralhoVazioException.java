package br.edu.uepb.map.framework.excecoes;

/**
 * Indica que uma compra foi solicitada quando o baralho nao possui cartas.
 */
public class BaralhoVazioException extends IllegalStateException {

    /**
     * Cria a excecao com uma mensagem que descreve a operacao invalida.
     */
    public BaralhoVazioException() {
        super("nao e possivel comprar de um baralho vazio");
    }
}

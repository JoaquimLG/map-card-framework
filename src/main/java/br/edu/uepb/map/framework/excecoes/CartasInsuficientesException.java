package br.edu.uepb.map.framework.excecoes;

/**
 * Indica que o baralho nao possui todas as cartas solicitadas em uma compra.
 */
public class CartasInsuficientesException extends IllegalStateException {

    /**
     * Cria a excecao descrevendo a quantidade solicitada e a disponivel.
     *
     * @param quantidadeSolicitada quantidade pedida na compra
     * @param quantidadeDisponivel quantidade presente no baralho
     */
    public CartasInsuficientesException(int quantidadeSolicitada, int quantidadeDisponivel) {
        super("foram solicitadas %d cartas, mas apenas %d estao disponiveis"
                .formatted(quantidadeSolicitada, quantidadeDisponivel));
    }
}

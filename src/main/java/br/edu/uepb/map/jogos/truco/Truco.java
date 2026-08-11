package br.edu.uepb.map.jogos.truco;

import br.edu.uepb.map.framework.baralho.Baralho;
import br.edu.uepb.map.framework.baralho.FabricaBaralho;
import br.edu.uepb.map.framework.cartas.CartaTradicional;

import java.util.Objects;

/**
 * Ponto inicial da integracao do jogo de Truco com o framework de cartas.
 *
 * <p>Esta classe apenas solicita a composicao do baralho; regras de forca,
 * pontuacao, distribuicao e vitoria continuam pertencendo ao jogo.</p>
 */
public class Truco {
    private final FabricaBaralho<CartaTradicional> fabricaBaralho;

    /**
     * Configura o jogo com a fabrica de sua composicao de cartas.
     *
     * @param fabricaBaralho fabrica usada para criar cada novo baralho
     * @throws NullPointerException se a fabrica for nula
     */
    public Truco(FabricaBaralho<CartaTradicional> fabricaBaralho) {
        this.fabricaBaralho = Objects.requireNonNull(
                fabricaBaralho, "fabricaBaralho nao pode ser nula");
    }

    /**
     * Solicita a fabrica configurada um novo baralho para o jogo.
     *
     * @return novo baralho fornecido pela fabrica
     * @throws NullPointerException se a fabrica violar seu contrato e retornar nulo
     */
    public Baralho<CartaTradicional> criarBaralho() {
        return Objects.requireNonNull(
                fabricaBaralho.criar(), "a fabrica nao pode retornar nulo");
    }
}

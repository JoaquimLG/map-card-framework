package br.edu.uepb.map.jogos.blackjack;

import java.util.List;
import java.util.Objects;

import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.regras.RegraJogo;

/**
 * Regras concretas do Blackjack: valida se uma jogada é permitida no
 * estado atual do jogador e determina o vencedor comparando a soma
 * das mãos, respeitando o limite de 21 pontos.
 */
public class RegraBlackjack implements RegraJogo {

    private final CalculadoraDeMao calculadora = new CalculadoraDeMao();

    /**
     * Cria uma regra de Blackjack, sem estado próprio além da
     * calculadora de pontuação.
     */
    public RegraBlackjack() {
    }

    /**
     * Verifica se a jogada é permitida: o jogador precisa estar ativo,
     * a jogada precisa ser uma {@link JogadaBlackjack} com ação
     * "COMPRAR" ou "PARAR", e não é permitido comprar mais cartas ao
     * atingir ou ultrapassar 21 pontos.
     *
     * @param jogada  jogada que o jogador deseja realizar
     * @param jogador jogador que está tentando realizar a jogada
     * @return {@code true} quando a jogada é permitida; {@code false}
     *         caso contrário
     * @throws NullPointerException se o jogador for {@code null}
     */
    @Override
    public boolean jogadaValida(Jogada jogada, Jogador jogador) {
        Objects.requireNonNull(jogador, "jogador nao pode ser nulo");
        // Jogador que ja parou ou ja estourou (ver Jogador.desativar())
        // nao pode mais jogar.
        if (!jogador.isAtivo()) {
            return false;
        }

        if (!(jogada instanceof JogadaBlackjack)) {
            return false;
        }

        JogadaBlackjack jogadaBlackjack = (JogadaBlackjack) jogada;
        String acao = jogadaBlackjack.getAcao();

        int pontuacao = calculadora.calcularPontuacao(jogador);

        // Se o jogador já estourou 21, não pode mais comprar cartas.
        if (pontuacao > 21 && acao.equals("COMPRAR")) {
            return false;
        }

        // Se chegou a 21, também não pode comprar mais cartas.
        if (pontuacao == 21 && acao.equals("COMPRAR")) {
            return false;
        }

        // As jogadas permitidas no Blackjack são COMPRAR e PARAR.
        return acao.equals("COMPRAR") || acao.equals("PARAR");
    }

    /**
     * Determina o vencedor comparando a soma da mão de cada jogador:
     * jogadores que ultrapassaram 21 pontos são desclassificados, e
     * entre os demais vence quem tiver a maior pontuação, sem empate.
     *
     * @param jogadores jogadores que participaram da partida
     * @return o jogador vencedor, ou {@code null} em caso de empate
     *         ou quando nenhum jogador ficou dentro do limite de 21
     * @throws NullPointerException se a lista de jogadores ou algum
     *                              de seus elementos for {@code null}
     */
    @Override
    public Jogador verificarVencedor(List<Jogador> jogadores) {
        Objects.requireNonNull(jogadores, "jogadores nao pode ser nulo");
        if (jogadores.isEmpty()) {
            return null;
        }
        if (jogadores.stream().anyMatch(Objects::isNull)) {
            throw new NullPointerException("jogadores nao pode conter elementos nulos");
        }

        Jogador vencedor = null;
        int maiorPontuacao = -1;

        for (Jogador jogador : jogadores) {
            int pontuacao = calculadora.calcularPontuacao(jogador);

            // Jogadores que estouraram 21 perdem.
            if (pontuacao > 21) {
                continue;
            }

            // Se encontrou uma pontuação maior, esse jogador passa a ser
            // o vencedor provisório.
            if (pontuacao > maiorPontuacao) {
                maiorPontuacao = pontuacao;
                vencedor = jogador;
            } else if (pontuacao == maiorPontuacao) {
                // Empate: não existe vencedor único.
                vencedor = null;
            }
        }

        return vencedor;
    }
}

package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.eventos.Evento;
import br.edu.uepb.map.framework.eventos.EventoPadrao;
import br.edu.uepb.map.framework.eventos.NotificadorEventos;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.partida.ExecutorDeJogada;
import br.edu.uepb.map.framework.partida.Partida;

import java.util.Objects;

/**
 * Aplica o efeito de uma {@link JogadaBlackjack} já validada por
 * {@link RegraBlackjack}: COMPRAR compra uma carta do baralho da partida (e
 * desativa o jogador se estourar 21); PARAR apenas desativa o jogador para a
 * rodada atual. Cada efeito dispara um {@link Evento} correspondente.
 *
 * @param <T> subtipo de carta usado pela partida (no jogo, {@code CartaTradicional})
 */
public class ExecutorDeJogadaBlackjack<T extends Carta> implements ExecutorDeJogada<T> {

    private final CalculadoraDeMao calculadora = new CalculadoraDeMao();
    private final NotificadorEventos notificador;

    /**
     * Cria um executor que notifica os eventos de compra/estouro/parada
     * através do notificador informado.
     *
     * @param notificador notificador usado para disparar os eventos
     *                    de cada efeito aplicado
     * @throws NullPointerException se o notificador for {@code null}
     */
    public ExecutorDeJogadaBlackjack(NotificadorEventos notificador) {
        this.notificador = Objects.requireNonNull(notificador, "notificador nao pode ser nulo");
    }

    @Override
    public void aplicar(Jogada jogada, Jogador jogador, Partida<T> partida) {
        Objects.requireNonNull(jogador, "jogador nao pode ser nulo");
        Objects.requireNonNull(partida, "partida nao pode ser nula");
        if (!(jogada instanceof JogadaBlackjack jogadaBlackjack)) {
            throw new IllegalArgumentException("jogada deve ser uma JogadaBlackjack");
        }

        switch (jogadaBlackjack.getAcao()) {
            case "COMPRAR" -> aplicarCompra(jogador, partida);
            case "PARAR" -> aplicarParada(jogador);
            default -> throw new IllegalArgumentException(
                    "acao de blackjack desconhecida: " + jogadaBlackjack.getAcao());
        }
    }

    private void aplicarCompra(Jogador jogador, Partida<T> partida) {
        T carta = partida.comprarCartaPara(jogador);
        notificador.notificar(new Evento(
                EventoPadrao.CARTA_COMPRADA, "comprou " + carta.getDescricao(), jogador));

        if (calculadora.calcularPontuacao(jogador) > 21) {
            jogador.desativar();
            notificador.notificar(new Evento(
                    EventoBlackjack.JOGADOR_ESTOUROU, "estourou 21 pontos", jogador));
        }
    }

    private void aplicarParada(Jogador jogador) {
        jogador.desativar();
        notificador.notificar(new Evento(EventoPadrao.JOGADOR_PAROU, "decidiu parar", jogador));
    }
}

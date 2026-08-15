package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.eventos.Evento;
import br.edu.uepb.map.framework.eventos.NotificadorEventos;
import br.edu.uepb.map.framework.eventos.TipoEvento;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.partida.ExecutorDeJogada;
import br.edu.uepb.map.framework.partida.Partida;

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

    public ExecutorDeJogadaBlackjack(NotificadorEventos notificador) {
        this.notificador = notificador;
    }

    @Override
    public void aplicar(Jogada jogada, Jogador jogador, Partida<T> partida) {
        JogadaBlackjack jogadaBlackjack = (JogadaBlackjack) jogada;

        switch (jogadaBlackjack.getAcao()) {
            case "COMPRAR" -> aplicarCompra(jogador, partida);
            case "PARAR" -> aplicarParada(jogador);
            default -> throw new IllegalStateException(
                    "acao de blackjack desconhecida: " + jogadaBlackjack.getAcao());
        }
    }

    private void aplicarCompra(Jogador jogador, Partida<T> partida) {
        T carta = partida.comprarCartaPara(jogador);
        notificador.notificar(new Evento(
                TipoEvento.CARTA_COMPRADA, "comprou " + carta.getDescricao(), jogador));

        if (calculadora.calcularPontuacao(jogador) > 21) {
            jogador.desativar();
            notificador.notificar(new Evento(
                    TipoEvento.JOGADOR_ESTOUROU, "estourou 21 pontos", jogador));
        }
    }

    private void aplicarParada(Jogador jogador) {
        jogador.desativar();
        notificador.notificar(new Evento(TipoEvento.JOGADOR_PAROU, "decidiu parar", jogador));
    }
}

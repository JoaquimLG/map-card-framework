package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.eventos.TipoEvento;

/**
 * Tipos de evento próprios das regras de Blackjack.
 */
public enum EventoBlackjack implements TipoEvento {
    /** Um jogador ultrapassou 21 pontos e saiu da rodada. */
    JOGADOR_ESTOUROU
}

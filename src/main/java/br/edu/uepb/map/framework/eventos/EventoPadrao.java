package br.edu.uepb.map.framework.eventos;

/**
 * Tipos de evento reutilizáveis em diferentes jogos de cartas.
 */
public enum EventoPadrao implements TipoEvento {
    /** A partida foi iniciada. */
    PARTIDA_INICIADA,
    /** Um jogador comprou uma carta do baralho. */
    CARTA_COMPRADA,
    /** Um jogador decidiu parar de jogar na rodada atual. */
    JOGADOR_PAROU,
    /** Um jogador tentou uma jogada não permitida pelas regras. */
    JOGADA_INVALIDA,
    /** A partida foi encerrada e o vencedor, se houver, foi apurado. */
    PARTIDA_FINALIZADA
}

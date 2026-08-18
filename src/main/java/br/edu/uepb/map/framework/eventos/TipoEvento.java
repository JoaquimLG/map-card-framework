package br.edu.uepb.map.framework.eventos;

/**
 * Tipos de evento que podem ser disparados durante uma partida.
 *
 * <p>O framework define apenas os tipos genéricos usados pela própria
 * {@link br.edu.uepb.map.framework.partida.Partida}; jogos concretos
 * usam esses mesmos tipos ao notificar eventos específicos de suas
 * jogadas (ver, por exemplo, {@code ExecutorDeJogadaBlackjack}).</p>
 */
public enum TipoEvento {
    /** A partida foi iniciada. */
    PARTIDA_INICIADA,
    /** Um jogador comprou uma carta do baralho. */
    CARTA_COMPRADA,
    /** Um jogador decidiu parar de jogar na rodada atual. */
    JOGADOR_PAROU,
    /** Um jogador estourou o limite de pontuação do jogo. */
    JOGADOR_ESTOUROU,
    /** Um jogador tentou uma jogada não permitida pelas regras. */
    JOGADA_INVALIDA,
    /** A partida foi encerrada e o vencedor (se houver) foi apurado. */
    PARTIDA_FINALIZADA
}

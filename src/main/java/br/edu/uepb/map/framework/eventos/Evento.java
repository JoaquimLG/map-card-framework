package br.edu.uepb.map.framework.eventos;

import br.edu.uepb.map.framework.jogador.Jogador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Representa algo que aconteceu durante uma partida (ex.: uma carta foi
 * comprada, um jogador parou ou estourou), usado pelo framework para
 * notificar interessados através de {@link NotificadorEventos}.
 *
 * <p>Um evento é imutável: guarda o {@link TipoEvento}, uma mensagem
 * descritiva, o jogador relacionado (quando houver) e o momento em que
 * foi criado.</p>
 */
public class Evento {
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final TipoEvento tipo;
    private final String mensagem;
    private final Jogador jogador;
    private final LocalDateTime momento;

    /**
     * Cria um evento associado a um jogador.
     *
     * @param tipo     tipo do evento
     * @param mensagem descrição do que aconteceu
     * @param jogador  jogador relacionado ao evento, ou {@code null}
     *                 quando o evento não é específico de um jogador
     * @throws NullPointerException se o tipo ou a mensagem forem
     *                              {@code null}
     */
    public Evento(TipoEvento tipo, String mensagem, Jogador jogador) {
        this.tipo = Objects.requireNonNull(tipo, "tipo do evento nao pode ser nulo");
        this.mensagem = Objects.requireNonNull(mensagem, "mensagem do evento nao pode ser nula");
        this.jogador = jogador;
        this.momento = LocalDateTime.now();
    }

    /**
     * Cria um evento sem jogador associado.
     *
     * @param tipo     tipo do evento
     * @param mensagem descrição do que aconteceu
     * @throws NullPointerException se o tipo ou a mensagem forem
     *                              {@code null}
     */
    public Evento(TipoEvento tipo, String mensagem) {
        this(tipo, mensagem, null);
    }

    /**
     * Retorna o tipo deste evento.
     *
     * @return o tipo, nunca {@code null}
     */
    public TipoEvento getTipo() {
        return tipo;
    }

    /**
     * Retorna a mensagem descritiva deste evento.
     *
     * @return a mensagem, nunca {@code null}
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Retorna o jogador associado a este evento.
     *
     * @return o jogador, ou {@code null} quando o evento não é
     *         específico de um jogador
     */
    public Jogador getJogador() {
        return jogador;
    }

    /**
     * Retorna o momento em que este evento foi criado.
     *
     * @return o momento de criação, nunca {@code null}
     */
    public LocalDateTime getMomento() {
        return momento;
    }

    /**
     * Retorna uma representação textual do evento, adequada para
     * exibição em console, incluindo horário, tipo, mensagem e o nome
     * do jogador (quando houver).
     *
     * @return a representação textual do evento
     */
    @Override
    public String toString() {
        String horario = momento.format(FORMATO_HORA);
        if (jogador != null) {
            return "[%s] %s - %s (%s)".formatted(horario, tipo, mensagem, jogador.getNome());
        }
        return "[%s] %s - %s".formatted(horario, tipo, mensagem);
    }
}

package br.edu.uepb.map.framework.eventos;

import br.edu.uepb.map.framework.jogador.Jogador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento {
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final TipoEvento tipo;
    private final String mensagem;
    private final Jogador jogador;
    private final LocalDateTime momento;

    public Evento(TipoEvento tipo, String mensagem, Jogador jogador) {
        if (tipo == null) {
            throw new IllegalArgumentException("tipo do evento nao pode ser nulo");
        }
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.jogador = jogador;
        this.momento = LocalDateTime.now();
    }

    public Evento(TipoEvento tipo, String mensagem) {
        this(tipo, mensagem, null);
    }

    public TipoEvento getTipo() {
        return tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public LocalDateTime getMomento() {
        return momento;
    }

    @Override
    public String toString() {
        String horario = momento.format(FORMATO_HORA);
        if (jogador != null) {
            return "[%s] %s - %s (%s)".formatted(horario, tipo, mensagem, jogador.getNome());
        }
        return "[%s] %s - %s".formatted(horario, tipo, mensagem);
    }
}

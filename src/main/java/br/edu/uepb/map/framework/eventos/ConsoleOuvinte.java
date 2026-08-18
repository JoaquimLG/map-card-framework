package br.edu.uepb.map.framework.eventos;

import java.util.Objects;

/**
 * Ouvinte concreto que exibe os eventos da partida no console,
 * usando a representação textual de {@link Evento#toString()}.
 */
public class ConsoleOuvinte implements OuvinteEvento {

    /**
     * Cria um ouvinte de console, sem estado próprio.
     */
    public ConsoleOuvinte() {
    }

    /**
     * Imprime o evento recebido na saída padrão.
     *
     * @param evento evento notificado
     * @throws NullPointerException se o evento for {@code null}
     */
    @Override
    public void aoReceberEvento(Evento evento) {
        System.out.println(Objects.requireNonNull(evento, "evento nao pode ser nulo"));
    }
}

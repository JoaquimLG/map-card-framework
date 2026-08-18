package br.edu.uepb.map.framework.eventos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NotificadorEventos {
    private final List<OuvinteEvento> ouvintes = new ArrayList<>();

    public void adicionarOuvinte(OuvinteEvento ouvinte) {
        ouvintes.add(Objects.requireNonNull(ouvinte, "ouvinte nao pode ser nulo"));
    }

    public void removerOuvinte(OuvinteEvento ouvinte) {
        ouvintes.remove(Objects.requireNonNull(ouvinte, "ouvinte nao pode ser nulo"));
    }

    public void notificar(Evento evento) {
        Objects.requireNonNull(evento, "evento nao pode ser nulo");
        for (OuvinteEvento ouvinte : List.copyOf(ouvintes)) {
            ouvinte.aoReceberEvento(evento);
        }
    }

    /** Exposto principalmente para testes; a lista real nunca e entregue diretamente. */
    public List<OuvinteEvento> getOuvintes() {
        return Collections.unmodifiableList(ouvintes);
    }
}

package br.edu.uepb.map.framework.eventos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificadorEventos {
    private final List<OuvinteEvento> ouvintes = new ArrayList<>();

    public void adicionarOuvinte(OuvinteEvento ouvinte) {
        if (ouvinte != null) {
            ouvintes.add(ouvinte);
        }
    }

    public void removerOuvinte(OuvinteEvento ouvinte) {
        ouvintes.remove(ouvinte);
    }

    public void notificar(Evento evento) {
        for (OuvinteEvento ouvinte : ouvintes) {
            ouvinte.aoReceberEvento(evento);
        }
    }

    /** Exposto principalmente para testes; a lista real nunca e entregue diretamente. */
    public List<OuvinteEvento> getOuvintes() {
        return Collections.unmodifiableList(ouvintes);
    }
}

package br.edu.uepb.map.framework.eventos;

/**
 * Papel de "observador" no padrao Observer: qualquer classe interessada em
 * saber o que acontece durante a partida implementa esta interface
 * e se registra em um {@link NotificadorEventos}.
*/

@FunctionalInterface
public interface OuvinteEvento {
    void aoReceberEvento(Evento evento);
}

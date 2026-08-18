package br.edu.uepb.map.framework.eventos;

/**
 * Papel de "observador" no padrao Observer: qualquer classe interessada em
 * saber o que acontece durante a partida implementa esta interface
 * e se registra em um {@link NotificadorEventos}.
*/

@FunctionalInterface
public interface OuvinteEvento {

    /**
     * Chamado pelo {@link NotificadorEventos} sempre que um evento é
     * notificado, para que este ouvinte reaja da forma que julgar
     * adequada (ex.: exibir no console, registrar em log).
     *
     * @param evento evento notificado
     */
    void aoReceberEvento(Evento evento);
}

package br.edu.uepb.map.framework.eventos;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificadorEventosTest {
    @Test
    void deveNotificarTodosOsOuvintesRegistrados() {
        NotificadorEventos notificador = new NotificadorEventos();
        List<Evento> recebidosPorA = new ArrayList<>();
        List<Evento> recebidosPorB = new ArrayList<>();
        notificador.adicionarOuvinte(recebidosPorA::add);
        notificador.adicionarOuvinte(recebidosPorB::add);

        Evento evento = new Evento(EventoPadrao.PARTIDA_INICIADA, "inicio da partida");
        notificador.notificar(evento);

        assertEquals(1, recebidosPorA.size());
        assertEquals(1, recebidosPorB.size());
        assertSame(evento, recebidosPorA.get(0));
    }

    @Test
    void ouvinteRemovidoNaoDeveSerNotificado() {
        NotificadorEventos notificador = new NotificadorEventos();
        List<Evento> recebidos = new ArrayList<>();
        OuvinteEvento ouvinte = recebidos::add;

        notificador.adicionarOuvinte(ouvinte);
        notificador.removerOuvinte(ouvinte);
        notificador.notificar(new Evento(EventoPadrao.PARTIDA_FINALIZADA, "fim"));

        assertTrue(recebidos.isEmpty());
    }

    @Test
    void naoDeveExporListaInternaDeOuvintesDiretamente() {
        NotificadorEventos notificador = new NotificadorEventos();
        notificador.adicionarOuvinte(evento -> { });

        assertThrows(UnsupportedOperationException.class,
                () -> notificador.getOuvintes().add(evento -> { }));
    }

    @Test
    void rejeitaOuvinteEEventoNulos() {
        NotificadorEventos notificador = new NotificadorEventos();

        assertThrows(NullPointerException.class, () -> notificador.adicionarOuvinte(null));
        assertThrows(NullPointerException.class, () -> notificador.removerOuvinte(null));
        assertThrows(NullPointerException.class, () -> notificador.notificar(null));
    }

    @Test
    void alteracaoDeCadastroDuranteNotificacaoSoAfetaProximoEvento() {
        NotificadorEventos notificador = new NotificadorEventos();
        List<Evento> recebidos = new ArrayList<>();
        OuvinteEvento novoOuvinte = recebidos::add;
        notificador.adicionarOuvinte(evento -> notificador.adicionarOuvinte(novoOuvinte));

        Evento primeiro = new Evento(EventoPadrao.PARTIDA_INICIADA, "primeiro");
        notificador.notificar(primeiro);
        assertTrue(recebidos.isEmpty());

        Evento segundo = new Evento(EventoPadrao.PARTIDA_FINALIZADA, "segundo");
        notificador.notificar(segundo);
        assertEquals(List.of(segundo), recebidos);
    }
}

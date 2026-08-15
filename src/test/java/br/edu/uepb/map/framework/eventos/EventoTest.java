package br.edu.uepb.map.framework.eventos;

import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.jogador.JogadorHumano;
import br.edu.uepb.map.framework.jogador.MaoDeCartas;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventoTest {
    @Test
    void deveCriarEventoComTipoEMensagemSemJogador() {
        Evento evento = new Evento(TipoEvento.CARTA_COMPRADA, "carta comprada do topo");

        assertEquals(TipoEvento.CARTA_COMPRADA, evento.getTipo());
        assertEquals("carta comprada do topo", evento.getMensagem());
        assertNull(evento.getJogador());
        assertNotNull(evento.getMomento());
    }

    @Test
    void deveRejeitarTipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Evento(null, "mensagem qualquer"));
    }

    @Test
    void toStringDeveConterTipoMensagemENomeDoJogadorQuandoPresente() {
        Jogador jogador = new JogadorHumano(
                "Ana", new MaoDeCartas<Carta>(), new Scanner(System.in), acao -> null);

        Evento evento = new Evento(TipoEvento.JOGADOR_ESTOUROU, "estourou 21", jogador);
        String texto = evento.toString();

        assertTrue(texto.contains("JOGADOR_ESTOUROU"));
        assertTrue(texto.contains("estourou 21"));
        assertTrue(texto.contains("Ana"));
    }

    @Test
    void toStringSemJogadorNaoContemParenteses() {
        Evento evento = new Evento(TipoEvento.PARTIDA_INICIADA, "inicio da partida");

        assertTrue(evento.toString().contains("PARTIDA_INICIADA"));
    }
}

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
        Evento evento = new Evento(EventoPadrao.CARTA_COMPRADA, "carta comprada do topo");

        assertEquals(EventoPadrao.CARTA_COMPRADA, evento.getTipo());
        assertEquals("carta comprada do topo", evento.getMensagem());
        assertNull(evento.getJogador());
        assertNotNull(evento.getMomento());
    }

    @Test
    void deveRejeitarTipoNulo() {
        assertThrows(NullPointerException.class,
                () -> new Evento(null, "mensagem qualquer"));
    }

    @Test
    void deveRejeitarMensagemNula() {
        assertThrows(NullPointerException.class,
                () -> new Evento(EventoPadrao.PARTIDA_INICIADA, null));
    }

    @Test
    void toStringDeveConterTipoMensagemENomeDoJogadorQuandoPresente() {
        Jogador jogador = new JogadorHumano(
                "Ana", new MaoDeCartas<Carta>(), new Scanner(System.in), acao -> null);

        Evento evento = new Evento(TipoEventoDeTeste.EVENTO_DO_JOGO, "evento do jogo", jogador);
        String texto = evento.toString();

        assertEquals(TipoEventoDeTeste.EVENTO_DO_JOGO, evento.getTipo());
        assertTrue(texto.contains("EVENTO_DO_JOGO"));
        assertTrue(texto.contains("evento do jogo"));
        assertTrue(texto.contains("Ana"));
    }

    @Test
    void toStringSemJogadorNaoContemParenteses() {
        Evento evento = new Evento(EventoPadrao.PARTIDA_INICIADA, "inicio da partida");

        assertTrue(evento.toString().contains("PARTIDA_INICIADA"));
    }

    private enum TipoEventoDeTeste implements TipoEvento {
        EVENTO_DO_JOGO
    }
}

package br.edu.uepb.map.framework.eventos;

import java.util.Objects;

//Ouvinte concreto para jogo no console
public class ConsoleOuvinte implements OuvinteEvento{
    @Override
    public void aoReceberEvento(Evento evento) {
        System.out.println(Objects.requireNonNull(evento, "evento nao pode ser nulo"));
    }
}

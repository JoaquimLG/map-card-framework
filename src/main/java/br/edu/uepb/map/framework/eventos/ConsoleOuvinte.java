package br.edu.uepb.map.framework.eventos;

//Ouvinte concreto para jogo no console
public class ConsoleOuvinte implements OuvinteEvento{
    @Override
    public void aoReceberEvento(Evento evento) {
        System.out.println(evento);
    }
}

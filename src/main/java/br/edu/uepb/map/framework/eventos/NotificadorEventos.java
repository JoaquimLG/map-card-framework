package br.edu.uepb.map.framework.eventos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Papel de "sujeito" no padrão de projeto <b>Observer</b>: mantém a
 * lista de {@link OuvinteEvento} interessados e notifica todos eles
 * sempre que um {@link Evento} acontece na partida.
 *
 * <p>O framework e os jogos concretos não precisam saber quem está
 * ouvindo — apenas registram um evento aqui, e cada ouvinte decide o
 * que fazer com ele (ex.: {@link ConsoleOuvinte} imprime no console).</p>
 */
public class NotificadorEventos {
    private final List<OuvinteEvento> ouvintes = new ArrayList<>();

    /**
     * Cria um notificador sem nenhum ouvinte registrado.
     */
    public NotificadorEventos() {
    }

    /**
     * Registra um ouvinte para receber os próximos eventos notificados.
     *
     * @param ouvinte ouvinte a adicionar
     * @throws NullPointerException se o ouvinte for {@code null}
     */
    public void adicionarOuvinte(OuvinteEvento ouvinte) {
        ouvintes.add(Objects.requireNonNull(ouvinte, "ouvinte nao pode ser nulo"));
    }

    /**
     * Remove um ouvinte previamente registrado.
     *
     * @param ouvinte ouvinte a remover
     * @throws NullPointerException se o ouvinte for {@code null}
     */
    public void removerOuvinte(OuvinteEvento ouvinte) {
        ouvintes.remove(Objects.requireNonNull(ouvinte, "ouvinte nao pode ser nulo"));
    }

    /**
     * Notifica todos os ouvintes registrados sobre o evento informado,
     * na ordem em que foram adicionados.
     *
     * @param evento evento a notificar
     * @throws NullPointerException se o evento for {@code null}
     */
    public void notificar(Evento evento) {
        Objects.requireNonNull(evento, "evento nao pode ser nulo");
        for (OuvinteEvento ouvinte : List.copyOf(ouvintes)) {
            ouvinte.aoReceberEvento(evento);
        }
    }

    /**
     * Retorna os ouvintes atualmente registrados.
     *
     * <p>Exposto principalmente para testes; a lista real nunca é
     * entregue diretamente, apenas uma visão somente leitura.</p>
     *
     * @return visão somente leitura dos ouvintes registrados
     */
    public List<OuvinteEvento> getOuvintes() {
        return Collections.unmodifiableList(ouvintes);
    }
}

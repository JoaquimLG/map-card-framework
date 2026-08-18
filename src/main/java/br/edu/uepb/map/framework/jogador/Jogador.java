package br.edu.uepb.map.framework.jogador;

import br.edu.uepb.map.framework.cartas.Carta;

import java.util.Objects;

/**
 * Representa um participante de uma partida, independente do jogo.
 *
 * <p>Um jogador tem nome, uma mão de cartas e um estado de atividade
 * (ativo/inativo) na rodada atual. A forma como ele decide sua jogada
 * é o ponto de extensão desta classe, delegado à subclasse através de
 * {@link #decidirJogada()} — {@code JogadorHumano} lê a entrada do
 * console, enquanto {@code JogadorAutomatico} delega a uma
 * {@link br.edu.uepb.map.framework.estrategia.Estrategia}.</p>
 */
public abstract class Jogador {

    private final String nome;
    private final MaoDeCartas<Carta> maoDeCartas;
    private boolean ativo;

    /**
     * Cria um jogador com o nome e a mão de cartas informados,
     * começando ativo na partida.
     *
     * @param nome        nome do jogador, usado para exibição e para
     *                    identificá-lo entre rodadas
     * @param maoDeCartas mão de cartas do jogador
     * @throws NullPointerException     se o nome ou a mão de cartas
     *                                  forem {@code null}
     * @throws IllegalArgumentException se o nome for vazio ou em branco
     */
    protected Jogador(String nome, MaoDeCartas<Carta> maoDeCartas) {
        this.nome = Objects.requireNonNull(nome, "nome nao pode ser nulo");
        if (nome.isBlank()) {
            throw new IllegalArgumentException("nome nao pode ser vazio");
        }
        this.maoDeCartas = Objects.requireNonNull(maoDeCartas, "maoDeCartas nao pode ser nula");
        this.ativo = true;
    }

    /**
     * Retorna o nome deste jogador.
     *
     * @return o nome, nunca {@code null}
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a mão de cartas deste jogador.
     *
     * @return a mão de cartas, nunca {@code null}
     */
    public MaoDeCartas<Carta> getMaoDeCartas() {
        return maoDeCartas;
    }

    /**
     * Indica se o jogador ainda participa ativamente da rodada atual.
     *
     * @return {@code true} quando o jogador ainda pode jogar;
     *         {@code false} quando já parou, estourou ou foi
     *         desativado por qualquer outro motivo específico do jogo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * Marca o jogador como inativo para o restante da rodada atual.
     *
     * <p>Usado, por exemplo, quando o jogador decide parar ou quando
     * uma regra de jogo o elimina (como estourar 21 no Blackjack).</p>
     */
    public void desativar() {
        this.ativo = false;
    }

    /**
     * Adiciona a carta informada à mão deste jogador.
     *
     * @param carta carta a receber
     * @throws NullPointerException se a carta for {@code null}
     */
    public void receberCarta(Carta carta) {
        maoDeCartas.receberCarta(carta);
    }

    /**
     * Decide a próxima jogada deste jogador.
     *
     * <p>Este é o ponto de extensão principal da classe: cada subtipo
     * de jogador define de onde vem essa decisão (entrada do usuário,
     * uma {@code Estrategia} automática, etc.).</p>
     *
     * @return a jogada escolhida, nunca {@code null}
     */
    public abstract Jogada decidirJogada();
}
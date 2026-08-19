package br.edu.uepb.map.framework.partida;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.edu.uepb.map.framework.baralho.Baralho;
import br.edu.uepb.map.framework.cartas.Carta;
import br.edu.uepb.map.framework.excecoes.CartasInsuficientesException;
import br.edu.uepb.map.framework.excecoes.JogadaInvalidaException;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.regras.RegraJogo;

/**
 * Define a estrutura base para uma partida de um jogo de cartas.
 *
 * <p>A classe utiliza o padrão de projeto <b>Template Method</b> para
 * definir o fluxo geral de inicialização e execução dos turnos de uma
 * partida, permitindo que subclasses especializem determinados
 * comportamentos sem alterar o algoritmo principal.</p>
 *
 * <p>A partida mantém os jogadores, o baralho, as regras do jogo e o
 * executor responsável por aplicar as jogadas.</p>
 *
 * <p>Os jogos concretos devem estender esta classe e implementar
 * {@link #deveEncerrar()} para definir quando a partida deve terminar.</p>
 *
 * @param <T> tipo de carta utilizado pelo baralho da partida
 */
public abstract class Partida<T extends Carta> {

    private final List<Jogador> jogadores;
    private final Baralho<T> baralho;
    private final RegraJogo regra;
    private final ExecutorDeJogada<T> executorDeJogada;

    private int indiceJogadorAtual;
    private boolean iniciada;
    private boolean encerrada;
    private Jogador vencedor;

    /**
     * Cria uma nova partida.
     *
     * <p>O construtor é protegido porque {@code Partida} representa uma
     * abstração do framework. As instâncias concretas devem ser criadas
     * por subclasses específicas de cada jogo.</p>
     *
     * @param jogadores jogadores que participarão da partida
     * @param baralho baralho utilizado durante a partida
     * @param regra regras responsáveis por validar jogadas e determinar
     *              o vencedor
     * @param executorDeJogada executor responsável por aplicar as jogadas
     *
     * @throws NullPointerException se {@code jogadores}, {@code baralho},
     *                              {@code regra} ou {@code executorDeJogada}
     *                              forem nulos
     * @throws IllegalArgumentException se a lista de jogadores estiver vazia
     * @throws NullPointerException se a lista de jogadores contiver algum
     *                              jogador nulo
     */
    protected Partida(
            List<Jogador> jogadores,
            Baralho<T> baralho,
            RegraJogo regra,
            ExecutorDeJogada<T> executorDeJogada) {

        Objects.requireNonNull(
                jogadores,
                "jogadores nao pode ser nulo");

        this.jogadores = new ArrayList<>(jogadores);

        if (this.jogadores.isEmpty()) {
            throw new IllegalArgumentException(
                    "partida precisa de ao menos um jogador");
        }

        if (this.jogadores.contains(null)) {
            throw new NullPointerException(
                    "jogadores nao pode conter elementos nulos");
        }

        this.baralho = Objects.requireNonNull(
                baralho,
                "baralho nao pode ser nulo");

        this.regra = Objects.requireNonNull(
                regra,
                "regra nao pode ser nula");

        this.executorDeJogada = Objects.requireNonNull(
                executorDeJogada,
                "executorDeJogada nao pode ser nulo");

        this.indiceJogadorAtual = 0;
        this.iniciada = false;
        this.encerrada = false;
        this.vencedor = null;
    }

    /**
     * Template Method responsável por iniciar a partida.
     *
     * <p>O algoritmo de inicialização segue as seguintes etapas:</p>
     *
     * <ol>
     *     <li>Valida o estado atual da partida.</li>
     *     <li>Valida a quantidade de cartas solicitada.</li>
     *     <li>Verifica se o baralho possui cartas suficientes.</li>
     *     <li>Executa {@link #prepararInicio()}.</li>
     *     <li>Distribui as cartas iniciais através de
     *         {@link #distribuirCartasIniciais(int)}.</li>
     *     <li>Marca a partida como iniciada.</li>
     *     <li>Define o primeiro jogador da partida.</li>
     *     <li>Executa {@link #aposInicio()}.</li>
     * </ol>
     *
     * <p>O método é {@code final} para impedir que subclasses alterem
     * o algoritmo principal definido pelo framework.</p>
     *
     * @param cartasIniciaisPorJogador quantidade de cartas que cada
     *                                 jogador receberá inicialmente
     *
     * @throws IllegalStateException se a partida já tiver sido iniciada
     *                               ou estiver encerrada
     * @throws IllegalArgumentException se a quantidade de cartas for negativa
     * @throws CartasInsuficientesException se o baralho não possuir
     *                                      cartas suficientes
     */
    public final void iniciar(int cartasIniciaisPorJogador) {

        if (iniciada) {
            throw new IllegalStateException(
                    "partida ja foi iniciada");
        }

        if (encerrada) {
            throw new IllegalStateException(
                    "partida ja encerrada");
        }

        if (cartasIniciaisPorJogador < 0) {
            throw new IllegalArgumentException(
                    "cartasIniciaisPorJogador nao pode ser negativo");
        }

        long quantidadeNecessaria =
                (long) cartasIniciaisPorJogador * jogadores.size();

        if (quantidadeNecessaria > baralho.tamanho()) {

            int quantidadeSolicitada =
                    quantidadeNecessaria > Integer.MAX_VALUE
                            ? Integer.MAX_VALUE
                            : (int) quantidadeNecessaria;

            throw new CartasInsuficientesException(
                    quantidadeSolicitada,
                    baralho.tamanho());
        }

        prepararInicio();

        distribuirCartasIniciais(
                cartasIniciaisPorJogador);

        this.iniciada = true;
        this.encerrada = false;
        this.vencedor = null;
        this.indiceJogadorAtual = 0;

        aposInicio();
    }

    /**
     * Hook chamado antes da distribuição das cartas iniciais.
     *
     * <p>Subclasses podem sobrescrever este método para executar
     * procedimentos específicos do jogo antes do início da partida.</p>
     *
     * <p>Por padrão, nenhuma ação é realizada.</p>
     */
    protected void prepararInicio() {
        // Comportamento padrão: nenhuma ação.
    }

    /**
     * Distribui as cartas iniciais para os jogadores.
     *
     * <p>A implementação padrão entrega a mesma quantidade de cartas
     * para cada jogador. Subclasses podem sobrescrever este método
     * caso o jogo possua uma forma diferente de distribuição.</p>
     *
     * @param cartasIniciaisPorJogador quantidade de cartas que cada
     *                                jogador receberá
     */
    protected void distribuirCartasIniciais(
            int cartasIniciaisPorJogador) {

        for (Jogador jogador : jogadores) {
            for (int i = 0;
                 i < cartasIniciaisPorJogador;
                 i++) {

                comprarCartaPara(jogador);
            }
        }
    }

    /**
     * Hook executado depois que a partida foi iniciada e as cartas
     * iniciais foram distribuídas.
     *
     * <p>Subclasses podem sobrescrever este método para executar
     * procedimentos específicos do jogo.</p>
     *
     * <p>Por padrão, nenhuma ação é realizada.</p>
     */
    protected void aposInicio() {
        // Comportamento padrão: nenhuma ação.
    }

    /**
     * Compra uma carta do baralho e a entrega ao jogador informado.
     *
     * <p>O jogador precisa fazer parte da partida para receber uma carta.</p>
     *
     * @param jogador jogador que receberá a carta
     *
     * @return carta comprada do baralho
     *
     * @throws NullPointerException se o jogador for nulo
     * @throws IllegalArgumentException se o jogador não participar
     *                                  desta partida
     * @throws br.edu.uepb.map.framework.excecoes.BaralhoVazioException se o
     *         baralho não possuir cartas
     */
    public T comprarCartaPara(Jogador jogador) {

        Objects.requireNonNull(
                jogador,
                "jogador nao pode ser nulo");

        if (!jogadores.contains(jogador)) {
            throw new IllegalArgumentException(
                    "jogador nao participa desta partida");
        }

        T carta = baralho.comprarCarta();

        jogador.receberCarta(carta);

        return carta;
    }

    /**
     * Retorna o jogador que possui a vez atualmente.
     *
     * @return jogador da vez
     */
    public Jogador getJogadorDaVez() {
        return jogadores.get(indiceJogadorAtual);
    }

    /**
     * Template Method responsável pela execução de um turno.
     *
     * <p>O algoritmo realiza as seguintes etapas:</p>
     *
     * <ol>
     *     <li>Verifica se a partida foi iniciada.</li>
     *     <li>Verifica se a partida já foi encerrada.</li>
     *     <li>Obtém o jogador da vez.</li>
     *     <li>Ignora jogadores que não estão ativos.</li>
     *     <li>Executa {@link #antesDoTurno(Jogador)}.</li>
     *     <li>Solicita a jogada ao jogador.</li>
     *     <li>Valida a jogada usando as regras do jogo.</li>
     *     <li>Aplica a jogada através do executor.</li>
     *     <li>Executa {@link #aposJogada(Jogador, Jogada)}.</li>
     *     <li>Verifica se a partida deve terminar.</li>
     *     <li>Avança para o próximo jogador, quando necessário.</li>
     * </ol>
     *
     * <p>O método é {@code final} para preservar o algoritmo definido
     * pelo Template Method.</p>
     *
     * @throws IllegalStateException se a partida ainda não foi iniciada
     *                               ou já foi encerrada
     * @throws NullPointerException se o jogador produzir uma jogada nula
     * @throws JogadaInvalidaException se a jogada realizada não for válida
     */
    public final void jogarTurno() {

        if (!iniciada) {
            throw new IllegalStateException(
                    "partida ainda nao foi iniciada; chame iniciar(int) antes");
        }

        if (encerrada) {
            throw new IllegalStateException(
                    "partida ja encerrada");
        }

        Jogador jogadorAtual = getJogadorDaVez();

        if (!jogadorAtual.isAtivo()) {
            avancarTurno();
            return;
        }

        antesDoTurno(jogadorAtual);

        Jogada jogada = Objects.requireNonNull(
                jogadorAtual.decidirJogada(),
                "jogador nao pode decidir uma jogada nula");

        if (!regra.jogadaValida(
                jogada,
                jogadorAtual)) {

            throw new JogadaInvalidaException(
                    jogadorAtual,
                    jogada);
        }

        executorDeJogada.aplicar(
                jogada,
                jogadorAtual,
                this);

        aposJogada(
                jogadorAtual,
                jogada);

        if (deveEncerrar()) {
            encerrarPartida();
            return;
        }

        avancarTurno();
    }

    /**
     * Hook executado antes de um jogador realizar sua jogada.
     *
     * <p>Subclasses podem sobrescrever este método para executar
     * comportamentos específicos antes de cada turno.</p>
     *
     * @param jogador jogador que realizará a jogada
     */
    protected void antesDoTurno(Jogador jogador) {
        // Comportamento padrão: nenhuma ação.
    }

    /**
     * Hook executado depois que uma jogada válida foi aplicada.
     *
     * <p>Subclasses podem sobrescrever este método para reagir à jogada
     * realizada, como registrar eventos ou atualizar informações
     * específicas da partida.</p>
     *
     * @param jogador jogador que realizou a jogada
     * @param jogada jogada realizada
     */
    protected void aposJogada(
            Jogador jogador,
            Jogada jogada) {
        // Comportamento padrão: nenhuma ação.
    }

    /**
     * Define a condição de encerramento específica da partida.
     *
     * <p>Este método representa um ponto de extensão do Template Method.
     * Cada jogo concreto deve determinar quando a partida deve ser
     * encerrada.</p>
     *
     * @return {@code true} quando a partida deve ser encerrada;
     *         {@code false} caso contrário
     */
    protected abstract boolean deveEncerrar();

    /**
     * Avança para o próximo jogador ativo.
     *
     * <p>Jogadores que não estejam ativos são ignorados. Caso nenhum
     * jogador esteja ativo, o índice atual não é alterado.</p>
     */
    private void avancarTurno() {

        if (jogadores.stream().noneMatch(Jogador::isAtivo)) {
            return;
        }

        do {
            indiceJogadorAtual =
                    (indiceJogadorAtual + 1)
                            % jogadores.size();

        } while (!jogadores
                .get(indiceJogadorAtual)
                .isAtivo());
    }

    /**
     * Encerra a partida e determina seu vencedor utilizando as regras
     * do jogo.
     *
     * <p>O vencedor é calculado através de
     * {@link RegraJogo#verificarVencedor(List)}.</p>
     *
     * @throws IllegalStateException se a partida ainda não tiver sido
     *                               iniciada ou já estiver encerrada
     */
    public void encerrarPartida() {

        if (!iniciada) {
            throw new IllegalStateException(
                    "partida ainda nao foi iniciada");
        }

        if (encerrada) {
            throw new IllegalStateException(
                    "partida ja encerrada");
        }

        Jogador vencedorApurado =
                regra.verificarVencedor(
                        List.copyOf(jogadores));

        this.vencedor = vencedorApurado;
        this.encerrada = true;
    }

    /**
     * Verifica se a partida já foi iniciada.
     *
     * @return {@code true} se a partida foi iniciada;
     *         {@code false} caso contrário
     */
    public boolean isIniciada() {
        return iniciada;
    }

    /**
     * Verifica se a partida já foi encerrada.
     *
     * @return {@code true} se a partida foi encerrada;
     *         {@code false} caso contrário
     */
    public boolean isEncerrada() {
        return encerrada;
    }

    /**
     * Retorna o vencedor da partida.
     *
     * @return jogador vencedor ou {@code null} quando ainda não existe
     *         um vencedor
     */
    public Jogador getVencedor() {
        return vencedor;
    }

    /**
     * Retorna uma cópia imutável da lista de jogadores da partida.
     *
     * <p>A coleção interna não é exposta diretamente, preservando o
     * encapsulamento da partida.</p>
     *
     * @return lista imutável contendo os jogadores da partida
     */
    public List<Jogador> getJogadores() {
        return List.copyOf(jogadores);
    }

    /**
     * Retorna o baralho utilizado pela partida.
     *
     * @return baralho da partida, nunca {@code null}
     */
    public Baralho<T> getBaralho() {
        return baralho;
    }
}

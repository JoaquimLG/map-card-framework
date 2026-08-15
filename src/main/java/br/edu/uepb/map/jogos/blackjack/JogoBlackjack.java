package br.edu.uepb.map.jogos.blackjack;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import br.edu.uepb.map.framework.baralho.Baralho;
import br.edu.uepb.map.framework.baralho.FabricaBaralhoTradicional;
import br.edu.uepb.map.framework.cartas.CartaTradicional;
import br.edu.uepb.map.framework.eventos.ConsoleOuvinte;
import br.edu.uepb.map.framework.eventos.Evento;
import br.edu.uepb.map.framework.eventos.NotificadorEventos;
import br.edu.uepb.map.framework.eventos.TipoEvento;
import br.edu.uepb.map.framework.excecoes.JogadaInvalidaException;
import br.edu.uepb.map.framework.jogador.Jogada;
import br.edu.uepb.map.framework.jogador.Jogador;
import br.edu.uepb.map.framework.jogador.JogadorAutomatico;
import br.edu.uepb.map.framework.jogador.JogadorHumano;
import br.edu.uepb.map.framework.jogador.MaoDeCartas;
import br.edu.uepb.map.framework.partida.Partida;

/**
 * Monta o jogo de Blackjack (baralho, jogadores, regra e execução de jogada)
 * e roda a partida no console. Ponto de entrada da aplicação.
 */
public class JogoBlackjack {

    private static final int CARTAS_INICIAIS_POR_JOGADOR = 2;

    private final Scanner scanner = new Scanner(System.in);
    private final CalculadoraDeMao calculadora = new CalculadoraDeMao();
    private final Pontuacao pontuacao = new Pontuacao();

    public static void main(String[] args) {
        new JogoBlackjack().jogar();
    }

    public void jogar() {
        System.out.println("=== BLACKJACK ===");
        System.out.println("Regras: peça carta ('c') ou pare ('p'). Quem chegar mais perto de 21 sem estourar vence.");

        boolean continuar = true;
        while (continuar) {
            jogarRodada();
            continuar = perguntarSeContinua();
        }

        System.out.println();
        System.out.println(pontuacao.placar());
        System.out.println("Até a próxima!");
    }

    /**
     * Pergunta se o jogador quer jogar outra rodada e insiste até receber
     * uma resposta reconhecida ('s' ou 'n'), em vez de tratar qualquer
     * entrada inesperada como "não" e encerrar silenciosamente.
     */
    private boolean perguntarSeContinua() {
        while (true) {
            System.out.print("\nJogar outra rodada? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            if (resposta.startsWith("s")) {
                return true;
            }
            if (resposta.startsWith("n")) {
                return false;
            }
            System.out.println("Não entendi. Digite 's' para jogar de novo ou 'n' para sair.");
        }
    }

    private void jogarRodada() {
        Baralho<CartaTradicional> baralho = new FabricaBaralhoTradicional().criar();
        baralho.embaralhar();

        NotificadorEventos notificador = new NotificadorEventos();
        notificador.adicionarOuvinte(new ConsoleOuvinte());

        Jogador jogador = new JogadorHumano("Jogador", new MaoDeCartas<>(), scanner, this::mapearJogada);
        Jogador banca = new JogadorAutomatico("Banca", new MaoDeCartas<>(), new EstrategiaBlackjack());
        List<Jogador> jogadores = Arrays.asList(jogador, banca);

        pontuacao.registrarParticipante(jogador);
        pontuacao.registrarParticipante(banca);

        Partida<CartaTradicional> partida = new Partida<>(
                jogadores,
                baralho,
                new RegraBlackjack(),
                new ExecutorDeJogadaBlackjack<>(notificador));

        partida.iniciar(CARTAS_INICIAIS_POR_JOGADOR);
        notificador.notificar(new Evento(TipoEvento.PARTIDA_INICIADA, "nova rodada de Blackjack"));

        System.out.println();
        mostrarMao(jogador);
        mostrarMao(banca);

        while (!partida.isEncerrada()) {
            Jogador daVez = partida.getJogadorDaVez();

            if (daVez == jogador && jogador.isAtivo()) {
                System.out.printf(
                        "%nSua vez (pontuação atual: %d). Digite 'c' para comprar ou 'p' para parar: ",
                        calculadora.calcularPontuacao(jogador));
            }

            try {
                partida.jogarTurno();
            } catch (JogadaInvalidaException excecao) {
                notificador.notificar(new Evento(TipoEvento.JOGADA_INVALIDA, excecao.getMessage(), daVez));
                System.out.println("Jogada inválida, tente novamente.");
                continue;
            }

            if (daVez == jogador && jogador.isAtivo()) {
                mostrarMao(jogador);
            }
        }

        notificador.notificar(new Evento(TipoEvento.PARTIDA_FINALIZADA, "rodada encerrada"));

        System.out.println();
        System.out.println("Resultado final:");
        mostrarMao(jogador);
        mostrarMao(banca);

        Jogador vencedor = partida.getVencedor();
        if (vencedor == null) {
            System.out.println("Empate! Ninguém venceu esta rodada.");
        } else {
            System.out.println(vencedor.getNome() + " venceu esta rodada!");
            pontuacao.registrarVitoria(vencedor);
        }
        System.out.println(pontuacao.placar());
    }

    private void mostrarMao(Jogador jogador) {
        System.out.println(jogador.getNome() + ": " + jogador.getMaoDeCartas().listarCartas()
                + " (" + calculadora.calcularPontuacao(jogador) + " pontos)");
    }

    private Jogada mapearJogada(String entrada) {
        String normalizada = entrada == null ? "" : entrada.trim().toUpperCase(Locale.ROOT);
        return switch (normalizada) {
            case "C", "COMPRAR", "1" -> new JogadaBlackjack("COMPRAR");
            case "P", "PARAR", "2" -> new JogadaBlackjack("PARAR");
            default -> new JogadaBlackjack(normalizada); // RegraBlackjack rejeita e pede de novo
        };
    }
}
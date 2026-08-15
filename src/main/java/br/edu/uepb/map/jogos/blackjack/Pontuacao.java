package br.edu.uepb.map.jogos.blackjack;

import br.edu.uepb.map.framework.jogador.Jogador;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controla o placar entre jogador e banca ao longo das rodadas de Blackjack.
 *
 * <p>Como cada rodada monta uma nova {@link br.edu.uepb.map.framework.partida.Partida}
 * (novo baralho, novos objetos {@link Jogador}), o placar identifica os
 * participantes pelo nome em vez de pela instância, para que as vitórias se
 * acumulem de uma rodada para a outra.</p>
 */
public class Pontuacao {

    private final Map<String, Integer> vitoriasPorNome = new LinkedHashMap<>();

    /**
     * Garante que o participante apareça no placar mesmo antes de vencer a
     * primeira rodada (útil para exibir "0 vitórias" desde o início).
     */
    public void registrarParticipante(Jogador jogador) {
        if (jogador != null) {
            vitoriasPorNome.putIfAbsent(jogador.getNome(), 0);
        }
    }

    /**
     * Soma uma vitória ao participante informado. Não faz nada em caso de
     * empate (vencedor nulo).
     */
    public void registrarVitoria(Jogador vencedor) {
        if (vencedor == null) {
            return;
        }
        vitoriasPorNome.merge(vencedor.getNome(), 1, Integer::sum);
    }

    public int getVitorias(Jogador jogador) {
        if (jogador == null) {
            return 0;
        }
        return vitoriasPorNome.getOrDefault(jogador.getNome(), 0);
    }

    /**
     * Representação do placar pronta para exibir no console.
     */
    public String placar() {
        StringBuilder texto = new StringBuilder("Placar -> ");
        vitoriasPorNome.forEach((nome, vitorias) ->
                texto.append(nome).append(": ").append(vitorias).append("  "));
        return texto.toString().trim();
    }
}

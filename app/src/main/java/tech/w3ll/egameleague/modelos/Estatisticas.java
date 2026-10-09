package tech.w3ll.egameleague.modelos;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que representa as estatísticas de um jogador ou equipe em um torneio.
 * Armazena dados como vitórias, derrotas, pontos e ranking.
 */
public class Estatisticas {
    private String nomeJogadorOuEquipe;
    private String nomeTorneio;
    private int pontos;
    private int partidasJogadas;
    private int vitorias;
    private int derrotas;
    private int ranking;

    /**
     * Construtor da classe. Inicializa os dados da estatística.
     */
    public Estatisticas(String nomeJogadorOuEquipe, String nomeTorneio, int partidasJogadas, int vitorias, int derrotas) {
        this.nomeJogadorOuEquipe = nomeJogadorOuEquipe;
        this.nomeTorneio = nomeTorneio;
        this.partidasJogadas = partidasJogadas;
        this.vitorias = vitorias;
        this.derrotas = derrotas;
        this.pontos = calcularPontuacao();
    }

    /**
     * Calcula a pontuação com base nas vitórias e derrotas.
     * Exemplo: vitória vale 15 pts, derrota vale 3 pts.
     */
    private int calcularPontuacao() {
        return (vitorias * 15) + (derrotas * 3);
    }

    public String getNomeJogadorOuEquipe() {
        return nomeJogadorOuEquipe;
    }

    public String getNomeTorneio() {
        return nomeTorneio;
    }

    public int getPontos() {
        return pontos;
    }

    public int getPartidasJogadas() {
        return partidasJogadas;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    /**
     * Adiciona uma vitória e atualiza os pontos e partidas jogadas.
     */
    public void adicionarVitoria() {
        this.vitorias++;
        this.partidasJogadas++;
        this.pontos = calcularPontuacao();
    }

    /**
     * Adiciona uma derrota e atualiza os pontos e partidas jogadas.
     */
    public void adicionarDerrota() {
        this.derrotas++;
        this.partidasJogadas++;
        this.pontos = calcularPontuacao();
    }

    /**
     * Calcula a porcentagem de vitórias em relação ao total de partidas jogadas.
     */
    public double calcularTaxaVitoria() {
        return partidasJogadas == 0 ? 0 : (double) vitorias / partidasJogadas * 100;
    }

    @Override
    public String toString() {
        return String.format("%s - Torneio %s: %d pts (%dV/%dD)",
                nomeJogadorOuEquipe, nomeTorneio, pontos, vitorias, derrotas);
    }

    /**
     * Define o ranking do jogador/equipe.
     */
    public void setRanking ( int ranking ) {
        this.ranking = ranking;
    }

    /**
     * Retorna o ranking do jogador/equipe.
     */
    public int getRanking () {
        return ranking;
    }

    // Lista que armazena as estatísticas de todos os jogadores ou equipes
    private List<Estatisticas> estatisticasList;

    /**
     * Construtor da classe. Inicializa a lista de estatísticas.
     */
    public Estatisticas() {
        this.estatisticasList = new ArrayList<>();
    }

    /**
     * Atualiza o ranking dos jogadores/equipes com base nos pontos, DENTRO DE CADA TORNEIO.
     * Agrupa as estatísticas por torneio, ordena cada grupo e define a posição (ranking) de cada um.
     */
    public void atualizarRanking() {
        // Agrupa as estatísticas por ID do torneio
        Map<String, List<Estatisticas>> estatisticasPorTorneio = estatisticasList.stream()
                .collect(Collectors.groupingBy(Estatisticas::getNomeTorneio));

        // Para cada torneio, ordena os participantes e atribui os rankings
        for (List<Estatisticas> statsDoTorneio : estatisticasPorTorneio.values()) {
            // Ordena por pontos em ordem decrescente
            statsDoTorneio.sort(Comparator.comparingInt(Estatisticas::getPontos).reversed());

            // Define o ranking para cada estatística dentro deste torneio específico
            for (int i = 0; i < statsDoTorneio.size(); i++) {
                statsDoTorneio.get(i).setRanking(i + 1);
            }
        }
        // A lista principal 'estatisticasList' não é reordenada globalmente,
        // mas os objetos Estatisticas dentro dela agora têm seus rankings específicos do torneio.
    }

    /**
     * Filtra as estatísticas apenas para um determinado torneio.
     * @param nomeTorneio Nome ou ID do torneio
     * @return Lista de estatísticas referentes ao torneio informado
     */
    public List<Estatisticas> filtrarPorTorneio(String nomeTorneio) {
        return estatisticasList.stream()
                .filter(e -> e.getNomeTorneio().equalsIgnoreCase(nomeTorneio))
                .collect(Collectors.toList());
    }

    /**
     * Registra uma vitória para o jogador/equipe em um torneio específico.
     * @param nomeJogadorOuEquipe Nome do jogador ou equipe
     * @param torneioId Nome ou ID do torneio
     */
    public void registrarVitoria(String nomeJogadorOuEquipe, String torneioId) {
        Estatisticas estatistica = buscarOuCriarEstatistica(nomeJogadorOuEquipe, torneioId);
        estatistica.adicionarVitoria();
        atualizarRanking(); // Atualiza os rankings considerando a separação por torneio
    }

    /**
     * Registra uma derrota para o jogador/equipe em um torneio específico.
     * @param nomeJogadorOuEquipe Nome do jogador ou equipe
     * @param nomeTorneio Nome ou ID do torneio
     */
    public void registrarDerrota(String nomeJogadorOuEquipe, String nomeTorneio) {
        Estatisticas estatistica = buscarOuCriarEstatistica(nomeJogadorOuEquipe, nomeTorneio);
        estatistica.adicionarDerrota();
        atualizarRanking(); // Atualiza os rankings considerando a separação por torneio
    }

    /**
     * Busca uma estatística existente ou cria uma nova, caso não exista.
     * @param nomeJogadorOuEquipe Nome do jogador ou equipe
     * @param nomeTorneio Nome ou ID do torneio
     * @return Objeto Estatisticas correspondente
     */
    private Estatisticas buscarOuCriarEstatistica(String nomeJogadorOuEquipe, String nomeTorneio) {
        for (Estatisticas estatistica : estatisticasList) {
            if (estatistica.getNomeJogadorOuEquipe().equalsIgnoreCase(nomeJogadorOuEquipe) &&
                    estatistica.getNomeTorneio().equalsIgnoreCase(nomeTorneio)) {
                return estatistica;
            }
        }

        // Se não encontrar, cria nova estatística
        Estatisticas novaEstatistica = new Estatisticas(
                nomeJogadorOuEquipe, nomeTorneio, 0, 0, 0);
        estatisticasList.add(novaEstatistica);
        return novaEstatistica;
    }

    /**
     * Retorna os rankings de todos os torneios como uma String formatada.
     * @return String com os rankings separados por torneio
     */
    public String exibirRanking() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== RANKINGS POR TORNEIO =====\n");

        if (estatisticasList.isEmpty()) {
            sb.append("Nenhuma estatística registrada.\n");
            return sb.toString();
        }

        // Agrupa as estatísticas por torneio para exibição
        Map<String, List<Estatisticas>> estatisticasPorTorneio = estatisticasList.stream()
                .collect(Collectors.groupingBy(Estatisticas::getNomeTorneio));

        // Ordena os nomes dos torneios para exibição consistente (opcional)
        List<String> nomesTorneiosOrdenados = new ArrayList<>(estatisticasPorTorneio.keySet());
        Collections.sort(nomesTorneiosOrdenados);

        for (String nomeTorneio : nomesTorneiosOrdenados) {
            sb.append("\n--- RANKING TORNEIO: ").append(nomeTorneio.toUpperCase()).append(" ---\n");
            List<Estatisticas> statsDoTorneio = estatisticasPorTorneio.get(nomeTorneio);

            // Ordena pela posição no ranking (que foi definida em atualizarRanking)
            // ou por pontos, se preferir recalcular a ordem aqui.
            statsDoTorneio.sort(Comparator.comparingInt(Estatisticas::getRanking));

            if (statsDoTorneio.isEmpty()) {
                sb.append("  Nenhum participante neste torneio.\n");
            } else {
                for (Estatisticas estatistica : statsDoTorneio) {
                    sb.append(String.format("  #%d - %s: %d pts (%dV/%dD)\n",
                            estatistica.getRanking(), // Usa o ranking específico do torneio
                            estatistica.getNomeJogadorOuEquipe(),
                            estatistica.getPontos(),
                            estatistica.getVitorias(),
                            estatistica.getDerrotas()));
                }
            }
        }
        return sb.toString();
    }

    /**
     * Retorna a lista de estatísticas (usado para consultas).
     */
    public List<Estatisticas> getEstatisticasList() {
        return estatisticasList;
    }
}

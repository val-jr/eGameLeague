package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Estatisticas;
import tech.w3ll.egameleague.repositores.InterfaceRepoListasEstatiscas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação do repositório de estatísticas e controladores de estatísticas usando listas.
 */
public class RepoListaEstatisticas implements InterfaceRepoListasEstatiscas {

    private final List<Estatisticas> estatisticas = new ArrayList<>();

    @Override
    public void adicionar(Estatisticas estatistica) {
        estatisticas.add(estatistica);
    }

    @Override
    public List<Estatisticas> listarTodos() {
        return new ArrayList<>(estatisticas);
    }

    @Override
    public Optional<Estatisticas> buscarPorNomeETorneio(String nome, String torneioId) {
        return estatisticas.stream()
                .filter(e -> e.getNomeJogadorOuEquipe().equals(nome)
                        && e.getNomeTorneio().equals(torneioId))
                .findFirst();
    }

    @Override
    public boolean atualizarEstatisticas(Estatisticas estatisticaAtualizada) {
        for (int i = 0; i < estatisticas.size(); i++) {
            Estatisticas e = estatisticas.get(i);
            if (e.getNomeJogadorOuEquipe().equals(estatisticaAtualizada.getNomeJogadorOuEquipe())
                    && e.getNomeTorneio().equals(estatisticaAtualizada.getNomeTorneio())) {
                estatisticas.set(i, estatisticaAtualizada);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removerEstatisticas(String nome, String torneioId) {
        return estatisticas.removeIf(e -> e.getNomeJogadorOuEquipe().equals(nome)
                && e.getNomeTorneio().equals(torneioId));
    }

    @Override
    public Optional<Estatisticas> buscarPorIndice(int indice) {
        if (indice >= 0 && indice < estatisticas.size()) {
            return Optional.of(estatisticas.get(indice));
        }
        return Optional.empty();
    }

    @Override
    public boolean atualizarPorIndice(int indice, Estatisticas controladorAtualizado) {
        if (indice >= 0 && indice < estatisticas.size()) {
            estatisticas.set(indice, controladorAtualizado);
            return true;
        }
        return false;
    }

    @Override
    public boolean removerPorIndice(int indice) {
        if (indice >= 0 && indice < estatisticas.size()) {
            estatisticas.remove(indice);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Estatisticas> buscarPrimeiro() {
        if (estatisticas.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(estatisticas.get(0));
    }
}

package tech.w3ll.egameleague.repositores;

import tech.w3ll.egameleague.modelos.Estatisticas;
import java.util.List;
import java.util.Optional;

/**
 * Interface unificada para repositório de Estatísticas e Controladores de Estatísticas.
 * Define métodos para adicionar, listar, buscar, atualizar e remover dados.
 */
public interface InterfaceRepoListasEstatiscas {

    // ---------- Métodos da InterfaceRepoListasEstatiscas ----------

    /**
     * Adiciona uma estatística ao repositório.
     */
    void adicionar(Estatisticas estatistica);

    /**
     * Lista todas as estatísticas presentes no repositório.
     */
    List<Estatisticas> listarTodos();

    /**
     * Busca estatísticas pelo nome do participante e torneio.
     */
    Optional<Estatisticas> buscarPorNomeETorneio(String nome, String torneioId);

    /**
     * Atualiza uma estatística específica.
     */
    boolean atualizarEstatisticas(Estatisticas estatisticaAtualizada);

    /**
     * Remove uma estatística pelo nome do participante e torneio.
     */
    boolean removerEstatisticas(String nome, String torneioId);

    // Métodos da Interface do antigo arquivo InterfaceRepoListasControladoEstatisticas

    /**
     * Busca um controlador pelo índice na lista.
     */
    Optional<Estatisticas> buscarPorIndice(int indice);

    /**
     * Atualiza um controlador pelo índice.
     */
    boolean atualizarPorIndice(int indice, Estatisticas controladorAtualizado);

    /**
     * Remove um controlador pelo índice.
     */
    boolean removerPorIndice(int indice);

    /**
     * Busca o primeiro controlador da lista, caso exista.
     */
    Optional<Estatisticas> buscarPrimeiro();
}

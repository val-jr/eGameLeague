package tech.w3ll.egameleague.repositores;

import tech.w3ll.egameleague.modelos.Time;

import java.util.List;

public interface ITimeRepo {

    void adicionar(Time time);
    void remover(Time time);
    List<Time> listarTodos();
    Time buscarPorNome(String nome);
    Time buscarPorId(int id);
    void limpar();
    void resetarTabela();

}

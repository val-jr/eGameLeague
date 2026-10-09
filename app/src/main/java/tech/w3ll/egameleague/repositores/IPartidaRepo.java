package tech.w3ll.egameleague.repositores;

import tech.w3ll.egameleague.modelos.Partida;

import java.util.List;

public interface IPartidaRepo {

    void adicionar(Partida partida);
    void remover(Partida partida);
    List<Partida> listarTodas();
    void limpar();
}

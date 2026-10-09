package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Partida;
import tech.w3ll.egameleague.repositores.IPartidaRepo;

import java.util.ArrayList;
import java.util.List;

public class PartidaRepoLista implements IPartidaRepo {
    private ArrayList<Partida> partidas = new ArrayList<>();

    public void adicionar(Partida partida) {
        partidas.add(partida);
    }

    public void remover(Partida partida) {
        partidas.remove(partida);
    }

    public List<Partida> listarTodas() {
        return new ArrayList<>(partidas);
    }

    public void limpar() {
        partidas.clear();
    }
}
package tech.w3ll.egameleague.modelos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tabela {
    private ArrayList<Time> times;
    private ArrayList<Partida> partidasAtuais;

    public Tabela(ArrayList<Time> times) {
        this.times = new ArrayList<>(times);
    }

    public ArrayList<Time> getTimes() {
        return this.times;
    }

    public void embaralharTimes() {
        Collections.shuffle(this.times);
    }

    public boolean temCampeao() {
        return this.times.size() <= 1;
    }

    public Time getCampeao() {
        if (this.times.size() == 1) {
            return this.times.get(0);
        }
        return null;
    }

    public void gerarConfrontos() {
        ArrayList<Time> emJogo = new ArrayList<>(this.times);
        Collections.shuffle(emJogo);

        partidasAtuais = new ArrayList<>();

        if (emJogo.size() % 2 != 0) {
            Time avanca = emJogo.remove(emJogo.size() - 1);
            partidasAtuais.add(new Partida(avanca, null));
        }

        for (int i = 0; i < emJogo.size(); i = i + 2) {
            partidasAtuais.add(new Partida(emJogo.get(i), emJogo.get(i + 1)));
        }
    }

    public void mostrarConfrontos() {
        System.out.println("\nConfrontos da rodada:");
        for (Partida p : partidasAtuais) {
            p.exibirPartida();
        }
    }

    public void adicionarTime(Time novoTime) {
        times.add(novoTime);
    }

    public ArrayList<Partida> getPartidasAtuais() {
        return this.partidasAtuais;
    }

    public void avancarParaProximaFase(List<Partida> partidasConcluidas) {
        ArrayList<Time> vencedores = new ArrayList<>();
        if (partidasConcluidas != null) {
            for (Partida p : partidasConcluidas) {
                if (p.getVencedor() != null) {
                    vencedores.add(p.getVencedor());
                } else if (p.getTime2() == null) {
                    vencedores.add(p.getTime1());
                }
            }
        }
        this.times = vencedores;
    }
}
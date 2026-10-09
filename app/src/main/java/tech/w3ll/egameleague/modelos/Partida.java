package tech.w3ll.egameleague.modelos;

public class Partida {
    private int id;
    private Time time1;
    private Time time2;
    private Time vencedor;

    public Partida(Time time1, Time time2) {
        this.time1 = time1;
        this.time2 = time2;
    }

    public void resetar() {
        if (this.vencedor != null) {
            if (this.vencedor.equals(this.time1)) {
                if (time2 != null) time2.anularEliminacao();
            } else {
                if (time1 != null) time1.anularEliminacao();
            }
        }
        this.vencedor = null;
    }

    public void definirVencedor(Time novoVencedor) {
        this.resetar();

        this.vencedor = novoVencedor;
        if (vencedor.equals(time1)) {
            if (time2 != null) {
                time2.eliminar();
            }
        } else {
            if (time1 != null) {
                time1.eliminar();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTime1() {
        return time1;
    }

    public Time getTime2() {
        return time2;
    }

    public Time getVencedor() {
        return vencedor;
    }

    public void exibirPartida() {
        if (time2 == null) {
            System.out.println(time1 + " avança automaticamente.");
        }
        else {
            System.out.println(time1 + " vs " + time2);
        }
    }
}
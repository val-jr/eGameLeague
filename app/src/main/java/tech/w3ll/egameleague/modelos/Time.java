package tech.w3ll.egameleague.modelos;

public class Time {
    private int id;
    private String nome;
    private boolean eliminado;

    public Time(String nome) {
        this.nome = nome;
        this.eliminado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void eliminar() {
        this.eliminado = true;
    }

    public void anularEliminacao() {
        this.eliminado = false;
    }

    @Override
    public String toString() {
        return nome;
    }
}
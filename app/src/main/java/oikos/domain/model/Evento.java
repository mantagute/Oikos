package oikos.domain.model;

public class Evento extends Entidade{
    private String nome;
    private int pontos;

    public Evento(String nome, int pontos) {
        super();
        this.nome = nome;
        this.pontos = pontos;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }
}

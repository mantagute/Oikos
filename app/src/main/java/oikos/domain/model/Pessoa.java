package oikos.domain.model;

public class Pessoa extends Entidade {
    String nome;

    public Pessoa(String nome){
        super();
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

package oikos.domain.model;

import oikos.domain.manager.Gerenciador;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import oikos.domain.interfaces.Autenticavel;

public class Paroquia extends Entidade implements Autenticavel{
    private String nome;
    private String senha;
    private Gerenciador<Grupo> gerenciadorGrupos;

    @JsonCreator
    public Paroquia(@JsonProperty("nome") String nome,@JsonProperty("senha") String senha) {
        this.nome = nome;
        this.senha = senha;
        this.gerenciadorGrupos = new Gerenciador<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean autenticarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    public Gerenciador<Grupo> getGerenciadorGrupos() {
        return gerenciadorGrupos;
    }

}

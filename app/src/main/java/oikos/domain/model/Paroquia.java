package oikos.domain.model;

import oikos.domain.manager.Gerenciador;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import oikos.domain.interfaces.Autenticavel;

/**
 * Representa uma Paróquia no sistema Oikos.
 * Uma paróquia pode gerenciar múltiplos grupos, enviar notificações
 * e solicitar vínculo com grupos.
 */
public class Paroquia extends Entidade implements Autenticavel{
    private String nome;
    @JsonProperty("senha")
    private String senha;
    private Gerenciador<Grupo> gerenciadorGrupos;

    /**
     * Cria uma nova Paróquia com nome e senha.
     *
     * @param nome  Nome da paróquia.
     * @param senha Senha de acesso da paróquia.
     */
    @JsonCreator
    public Paroquia(@JsonProperty("nome") String nome,@JsonProperty("senha") String senha) {
        this.nome = nome;
        this.senha = senha;
        this.gerenciadorGrupos = new Gerenciador<>();
    }

    /**
     * Retorna o nome da paróquia.
     *
     * @return Nome da paróquia como {@code String}.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome da paróquia.
     *
     * @param nome Novo nome da paróquia.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Valida se a senha informada corresponde à senha da paróquia.
     *
     * @param senhaInformada Senha a ser testada.
     * @return {@code true} se a senha estiver correta, {@code false} caso contrário.
     */
    @Override
    public boolean autenticarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    /**
     * Retorna o gerenciador de grupos vinculados a esta paróquia.
     *
     * @return {@link Gerenciador} de {@link Grupo}.
     */
    public Gerenciador<Grupo> getGerenciadorGrupos() {
        return gerenciadorGrupos;
    }

}

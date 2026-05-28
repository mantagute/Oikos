package oikos.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa um membro pertencente a um Grupo.
 * Cada pessoa é identificada unicamente pelo UUID herdado de {@link Entidade}
 * e possui um nome para identificação no sistema.
 */
public class Pessoa extends Entidade {
    String nome;

    /**
     * Cria uma nova Pessoa com o nome especificado.
     *
     * @param nome O nome do membro a ser cadastrado no grupo.
     */
    @JsonCreator
    public Pessoa(@JsonProperty("nome") String nome) {
        super();
        this.nome = nome;
    }

    /**
     * Retorna o nome desta pessoa.
     *
     * @return O nome do membro como {@code String}.
     */
    public String getNome() {
        return nome;
    }
}

package oikos.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa uma atividade pontuável realizada no contexto de um Grupo.
 * Cada evento possui um nome descritivo e um valor de pontos que será
 * atribuído ao participante ao realizá-lo.
 */
public class Evento extends Entidade {
    private String nome;
    private int pontos;

    /**
     * Cria um novo Evento com nome e valor de pontos definidos.
     *
     * @param nome   O nome descritivo da atividade (ex: "Leitura Bíblica").
     * @param pontos A quantidade de pontos que este evento vale ao ser realizado.
     */
    @JsonCreator
    public Evento(@JsonProperty("nome") String nome,@JsonProperty("pontos") int pontos) {
        super();
        this.nome = nome;
        this.pontos = pontos;
    }

    /**
     * Retorna o nome descritivo deste evento.
     *
     * @return O nome do evento como {@code String}.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a pontuação que este evento concede ao participante.
     *
     * @return A quantidade de pontos deste evento como {@code int}.
     */
    public int getPontos() {
        return pontos;
    }
}

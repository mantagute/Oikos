package oikos.domain.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa uma notificação enviada por uma {@link Paroquia} a um {@link Grupo}.
 * Cada notificação possui uma mensagem, o identificador da paróquia remetente
 * e um estado de leitura.
 */
public class Notificacao extends Entidade {

    private String mensagem;
    private UUID idParoquia;
    private boolean lida;

    /**
     * Cria uma nova Notificação com mensagem e paróquia remetente definidos.
     * O estado inicial de leitura é sempre {@code false}.
     *
     * @param mensagem   Conteúdo da notificação.
     * @param idParoquia UUID da paróquia que enviou a notificação.
     */
    @JsonCreator
    public Notificacao(@JsonProperty("mensagem") String mensagem,@JsonProperty("idParoquia") UUID idParoquia) {
        this.mensagem = mensagem;
        this.idParoquia = idParoquia;
        this.lida = false;
    }

    /**
     * Retorna o conteúdo da notificação.
     *
     * @return A mensagem como {@code String}.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Atualiza o conteúdo da notificação.
     *
     * @param mensagem Nova mensagem.
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Retorna o UUID da paróquia que enviou esta notificação.
     *
     * @return UUID da paróquia remetente.
     */
    public UUID getIdParoquia() {
        return idParoquia;
    }

    /**
     * Indica se a notificação já foi lida pelo grupo destinatário.
     *
     * @return {@code true} se já foi lida, {@code false} caso contrário.
     */
    public Boolean isLida() {
        return lida;
    }

    /**
     * Marca a notificação como lida.
     */
    public void marcarComoLida() {
        this.lida = true;
    }
}

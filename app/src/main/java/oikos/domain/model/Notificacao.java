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
    private String tipo;

    /**
     * Cria uma nova Notificação com mensagem, paróquia remetente e tipo definidos.
     * O estado inicial de leitura é sempre {@code false}.
     * O tipo padrão é "COMUM".
     *
     * @param mensagem   Conteúdo da notificação.
     * @param idParoquia UUID da paróquia que enviou a notificação.
     */
    @JsonCreator
    public Notificacao(@JsonProperty("mensagem") String mensagem,@JsonProperty("idParoquia") UUID idParoquia) {
        this.mensagem = mensagem;
        this.idParoquia = idParoquia;
        this.lida = false;
        this.tipo = "COMUM";
    }

    /**
     * Cria uma nova Notificação com tipo personalizado.
     *
     * @param mensagem   Conteúdo da notificação.
     * @param idParoquia UUID da paróquia remetente.
     * @param tipo       Tipo da notificação ("COMUM" ou "VINCULO").
     */
    public Notificacao(String mensagem, UUID idParoquia, String tipo) {
        this.mensagem = mensagem;
        this.idParoquia = idParoquia;
        this.lida = false;
        this.tipo = (tipo != null) ? tipo : "COMUM";
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
    public boolean isLida() {
        return lida;
    }

    /**
     * Retorna o tipo da notificação.
     *
     * @return "COMUM" para notificação padrão, "VINCULO" para solicitação de vínculo.
     */
    public String getTipo() {
        return (tipo != null) ? tipo : "COMUM";
    }

    /**
     * Marca a notificação como lida.
     */
    public void marcarComoLida() {
        this.lida = true;
    }
}

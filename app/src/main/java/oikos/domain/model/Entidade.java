package oikos.domain.model;

import java.util.UUID;

/**
 * Classe base abstrata para todas as entidades do domínio.
 * Fornece a identidade única (UUID) padronizada para todas as subclasses.
 */
public abstract class Entidade {
    private UUID id;

    /**
     * Inicializa a entidade gerando automaticamente um identificador único (UUID).
     */
    protected Entidade() {
        this.id = UUID.randomUUID();
    }

    /**
     * Retorna o identificador único desta entidade.
     *
     * @return O {@link UUID} que identifica esta entidade de forma única no sistema.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Define o identificador único desta entidade.
     * Utilizado durante a desserialização para restaurar o UUID persistido.
     *
     * @param id O {@link UUID} a ser atribuído a esta entidade.
     */
    public void setId(UUID id) {
        this.id = id;
    }
}

package oikos.domain.model;

import java.util.UUID;

public abstract class Entidade {
    private UUID Id;

    protected Entidade() {
        this.Id = UUID.randomUUID();
    }

    public UUID getId() {
        return Id;
    }
}

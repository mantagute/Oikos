package oikos.api.paroquia;

import java.util.UUID;
import oikos.domain.model.Paroquia;

public record ParoquiaResponse(UUID id, String nomem, int totalGrupos) {

    public static ParoquiaResponse from(Paroquia paroquia) {
        return new ParoquiaResponse(paroquia.getId(),paroquia.getNome(),paroquia.getGerenciadorGrupos().getListaEntidades().size());
    }
}

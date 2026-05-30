package oikos.api.grupo;

import java.util.UUID;

public record PontuarRequest(UUID pessoaId, UUID eventoId) {
}

package oikos.api.paroquia;

import java.util.List;
import java.util.UUID;

public record EnviarNotificacaoRequest(String mensagem, List<UUID> gruposIds) {
}


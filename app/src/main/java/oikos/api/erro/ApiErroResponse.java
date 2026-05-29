package oikos.api.erro;

import java.time.Instant;

public record ApiErroResponse(
        Instant timestamp,
        int status,
        String erro,
        String mensagem,
        String path) {
}

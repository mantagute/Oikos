package oikos.api.notificacao;

import java.util.UUID;

public record CriarNotificacaoRequest(String mensagem, UUID idParoquia, String tipo) {
}

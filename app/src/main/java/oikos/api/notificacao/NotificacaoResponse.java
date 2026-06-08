package oikos.api.notificacao;

import java.util.UUID;

import oikos.domain.model.Notificacao;

public record NotificacaoResponse(UUID id, String mensagem, UUID idParoquia, boolean lida) {

    public static NotificacaoResponse from(Notificacao notificacao) {
        return new NotificacaoResponse(notificacao.getId(), notificacao.getMensagem(), notificacao.getIdParoquia(), notificacao.isLida());
    }
    
}

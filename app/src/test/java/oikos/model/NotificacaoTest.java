package oikos.model;

import oikos.domain.model.Notificacao;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class NotificacaoTest {

    @Test
    void construtorTresParametrosDefineTipo() {
        Notificacao notificacao = new Notificacao("Solicitação", UUID.randomUUID(), "VINCULO");
        assertEquals("VINCULO", notificacao.getTipo());
    }

    @Test
    void construtorTresParametrosComTipoNuloUsaComum() {
        Notificacao notificacao = new Notificacao("Teste", UUID.randomUUID(), null);
        assertEquals("COMUM", notificacao.getTipo());
    }

    @Test
    void setMensagemAlteraConteudo() {
        Notificacao notificacao = new Notificacao("Original", UUID.randomUUID());
        notificacao.setMensagem("Modificada");
        assertEquals("Modificada", notificacao.getMensagem());
    }

    @Test
    void marcarComoLidaAlteraEstado() {
        Notificacao notificacao = new Notificacao("Teste", UUID.randomUUID());
        assertFalse(notificacao.isLida());
        notificacao.marcarComoLida();
        assertTrue(notificacao.isLida());
    }

    @Test
    void notificacaoPossuiIdUnico() {
        Notificacao n1 = new Notificacao("Msg1", UUID.randomUUID());
        Notificacao n2 = new Notificacao("Msg2", UUID.randomUUID());
        assertNotEquals(n1.getId(), n2.getId());
    }
}

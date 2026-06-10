package oikos.service;

import oikos.domain.model.Grupo;
import oikos.domain.model.Notificacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServicoNotificacoesTest {

    private ServicoGrupos servicoGrupos;
    private ServicoParoquias servicoParoquias;
    private ServicoNotificacoes servicoNotificacoes;

    @BeforeEach
    void setUp() {
        servicoGrupos = new ServicoGruposParaTeste();
        servicoParoquias = new ServicoParoquias(servicoGrupos);
        servicoNotificacoes = new ServicoNotificacoes(servicoGrupos, servicoParoquias);
    }

    @Test
    void adicionarNotificacaoValida() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        Notificacao notificacao = new Notificacao("Mensagem", UUID.randomUUID());
        servicoNotificacoes.adicionar(grupo.getId(), notificacao);
        assertEquals(1, servicoNotificacoes.getLista(grupo.getId()).size());
        assertEquals("Mensagem",
            servicoNotificacoes.getPorId(grupo.getId(), notificacao.getId()).getMensagem());
    }

    @Test
    void adicionarNotificacaoMensagemVaziaLancaExcecao() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        Notificacao notificacao = new Notificacao("", UUID.randomUUID());
        assertThrows(IllegalArgumentException.class,
            () -> servicoNotificacoes.adicionar(grupo.getId(), notificacao));
    }

    @Test
    void adicionarNotificacaoMensagemNulaLancaExcecao() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        Notificacao notificacao = new Notificacao(null, UUID.randomUUID());
        assertThrows(IllegalArgumentException.class,
            () -> servicoNotificacoes.adicionar(grupo.getId(), notificacao));
    }

    @Test
    void getListaRetornaListaVaziaQuandoSemNotificacoes() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        assertTrue(servicoNotificacoes.getLista(grupo.getId()).isEmpty());
    }

    @Test
    void getPorIdRetornaNotificacaoCorreta() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        Notificacao n1 = new Notificacao("Primeira", UUID.randomUUID());
        Notificacao n2 = new Notificacao("Segunda", UUID.randomUUID());
        servicoNotificacoes.adicionar(grupo.getId(), n1);
        servicoNotificacoes.adicionar(grupo.getId(), n2);
        assertEquals("Primeira",
            servicoNotificacoes.getPorId(grupo.getId(), n1.getId()).getMensagem());
        assertEquals("Segunda",
            servicoNotificacoes.getPorId(grupo.getId(), n2.getId()).getMensagem());
    }

    @Test
    void getPorIdInexistenteLancaExcecao() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        assertThrows(NoSuchElementException.class,
            () -> servicoNotificacoes.getPorId(grupo.getId(), UUID.randomUUID()));
    }

    @Test
    void removerNotificacao() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        Notificacao notificacao = new Notificacao("Mensagem", UUID.randomUUID());
        servicoNotificacoes.adicionar(grupo.getId(), notificacao);
        servicoNotificacoes.remover(grupo.getId(), notificacao.getId());
        assertTrue(servicoNotificacoes.getLista(grupo.getId()).isEmpty());
    }

    @Test
    void removerNotificacaoInexistenteLancaExcecao() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        assertThrows(NoSuchElementException.class,
            () -> servicoNotificacoes.remover(grupo.getId(), UUID.randomUUID()));
    }

    @Test
    void marcarComoLida() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        Notificacao notificacao = new Notificacao("Mensagem", UUID.randomUUID());
        servicoNotificacoes.adicionar(grupo.getId(), notificacao);
        servicoNotificacoes.marcarComoLida(grupo.getId(), notificacao.getId());
        assertTrue(
            servicoNotificacoes.getPorId(grupo.getId(), notificacao.getId()).isLida());
    }

    @Test
    void aceitarVinculoComSucesso() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        var paroquia = servicoParoquias.criar("Paróquia", "456");
        servicoParoquias.solicitarVinculo(paroquia.getId(), grupo.getId());
        Notificacao notificacao = grupo.getGerenciadorNotificacoes().getListaEntidades().get(0);
        servicoNotificacoes.aceitarVinculo(grupo.getId(), notificacao.getId());
        assertEquals(1, paroquia.getGerenciadorGrupos().getListaEntidades().size());
        assertEquals(grupo.getId(),
            paroquia.getGerenciadorGrupos().getListaEntidades().get(0).getId());
        assertTrue(grupo.getGerenciadorNotificacoes().getListaEntidades().isEmpty());
    }

    @Test
    void aceitarVinculoComNotificacaoNaoVinculoLancaExcecao() {
        Grupo grupo = servicoGrupos.criar("Grupo", "123");
        Notificacao notificacao = new Notificacao("Normal", UUID.randomUUID());
        servicoNotificacoes.adicionar(grupo.getId(), notificacao);
        assertThrows(IllegalArgumentException.class,
            () -> servicoNotificacoes.aceitarVinculo(grupo.getId(), notificacao.getId()));
    }

    @Test
    void operacoesComGrupoInexistente() {
        UUID idInexistente = UUID.randomUUID();
        assertThrows(NoSuchElementException.class,
            () -> servicoNotificacoes.getLista(idInexistente));
        assertThrows(NoSuchElementException.class,
            () -> servicoNotificacoes.getPorId(idInexistente, UUID.randomUUID()));
    }

    private static class ServicoGruposParaTeste extends ServicoGrupos {
        @Override
        public String salvar() {
            return "teste";
        }
    }
}

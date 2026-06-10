package oikos.service;

import oikos.domain.model.Grupo;
import oikos.domain.model.Paroquia;
import oikos.domain.model.Notificacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServicoParoquiasTest {

    private ServicoGrupos servicoGrupos;
    private ServicoParoquias servicoParoquias;

    @BeforeEach
    void setUp() {
        servicoGrupos = new ServicoGruposParaTeste();
        servicoParoquias = new ServicoParoquias(servicoGrupos);
    }

    @Test
    void criarParoquiaComDadosValidos() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia Teste", "1234");
        assertNotNull(paroquia.getId());
        assertEquals("Paróquia Teste", paroquia.getNome());
        assertEquals(1, servicoParoquias.getLista().size());
    }

    @Test
    void criarParoquiaComNomeDuplicadoLancaExcecao() {
        servicoParoquias.criar("Minha Paróquia", "1234");
        assertThrows(IllegalArgumentException.class,
            () -> servicoParoquias.criar("minha paróquia", "5678"));
    }

    @Test
    void excluirParoquiaComSenhaCorreta() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "1234");
        servicoParoquias.excluir(paroquia.getId(), "1234");
        assertTrue(servicoParoquias.getLista().isEmpty());
    }

    @Test
    void excluirParoquiaComSenhaIncorretaLancaExcecao() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "1234");
        assertThrows(SecurityException.class,
            () -> servicoParoquias.excluir(paroquia.getId(), "senha-errada"));
        assertEquals(1, servicoParoquias.getLista().size());
    }

    @Test
    void getPorIdRetornaParoquiaCorreta() {
        Paroquia p1 = servicoParoquias.criar("Paróquia A", "123");
        servicoParoquias.criar("Paróquia B", "456");
        assertEquals("Paróquia A", servicoParoquias.getPorId(p1.getId()).getNome());
    }

    @Test
    void instanciarCriaParoquia() {
        Paroquia paroquia = servicoParoquias.instanciar("Teste", "senha");
        assertNotNull(paroquia);
        assertEquals("Teste", paroquia.getNome());
    }

    @Test
    void getNomeAdequado() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia A", "123");
        assertEquals("Paróquia A", servicoParoquias.getNome(paroquia));
    }

    @Test
    void vincularGrupoValido() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        Grupo grupo = servicoGrupos.criar("Grupo", "456");
        servicoParoquias.vincularGrupo(paroquia.getId(), grupo.getId());
        assertEquals(1, paroquia.getGerenciadorGrupos().getListaEntidades().size());
        assertEquals(grupo.getId(),
            paroquia.getGerenciadorGrupos().getListaEntidades().get(0).getId());
    }

    @Test
    void vincularGrupoJaVinculadoLancaExcecao() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        Grupo grupo = servicoGrupos.criar("Grupo", "456");
        servicoParoquias.vincularGrupo(paroquia.getId(), grupo.getId());
        assertThrows(IllegalArgumentException.class,
            () -> servicoParoquias.vincularGrupo(paroquia.getId(), grupo.getId()));
    }

    @Test
    void desvincularGrupo() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        Grupo grupo = servicoGrupos.criar("Grupo", "456");
        servicoParoquias.vincularGrupo(paroquia.getId(), grupo.getId());
        servicoParoquias.desvincularGrupo(paroquia.getId(), grupo.getId());
        assertTrue(paroquia.getGerenciadorGrupos().getListaEntidades().isEmpty());
    }

    @Test
    void desvincularGrupoNaoVinculadoNaoLancaExcecao() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        assertDoesNotThrow(
            () -> servicoParoquias.desvincularGrupo(paroquia.getId(), UUID.randomUUID()));
    }

    @Test
    void solicitarVinculoCriaNotificacaoNoGrupo() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        Grupo grupo = servicoGrupos.criar("Grupo", "456");
        servicoParoquias.solicitarVinculo(paroquia.getId(), grupo.getId());
        List<Notificacao> notificacoes = grupo.getGerenciadorNotificacoes().getListaEntidades();
        assertEquals(1, notificacoes.size());
        assertEquals("VINCULO", notificacoes.get(0).getTipo());
        assertEquals(paroquia.getId(), notificacoes.get(0).getIdParoquia());
    }

    @Test
    void solicitarVinculoDuplicadoLancaExcecao() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        Grupo grupo = servicoGrupos.criar("Grupo", "456");
        servicoParoquias.solicitarVinculo(paroquia.getId(), grupo.getId());
        assertThrows(IllegalArgumentException.class,
            () -> servicoParoquias.solicitarVinculo(paroquia.getId(), grupo.getId()));
    }

    @Test
    void solicitarVinculoGrupoJaVinculadoLancaExcecao() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        Grupo grupo = servicoGrupos.criar("Grupo", "456");
        servicoParoquias.vincularGrupo(paroquia.getId(), grupo.getId());
        assertThrows(IllegalArgumentException.class,
            () -> servicoParoquias.solicitarVinculo(paroquia.getId(), grupo.getId()));
    }

    @Test
    void enviarNotificacoesParaGruposEspecificos() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        Grupo grupo1 = servicoGrupos.criar("Grupo 1", "456");
        Grupo grupo2 = servicoGrupos.criar("Grupo 2", "789");
        servicoParoquias.vincularGrupo(paroquia.getId(), grupo1.getId());
        servicoParoquias.vincularGrupo(paroquia.getId(), grupo2.getId());
        servicoParoquias.enviarNotificacoes(paroquia.getId(), "Mensagem", List.of(grupo1.getId()));
        assertEquals(1, grupo1.getGerenciadorNotificacoes().getListaEntidades().size());
        assertTrue(grupo2.getGerenciadorNotificacoes().getListaEntidades().isEmpty());
    }

    @Test
    void enviarNotificacoesParaTodosOsGrupos() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        Grupo grupo1 = servicoGrupos.criar("Grupo 1", "456");
        Grupo grupo2 = servicoGrupos.criar("Grupo 2", "789");
        servicoParoquias.vincularGrupo(paroquia.getId(), grupo1.getId());
        servicoParoquias.vincularGrupo(paroquia.getId(), grupo2.getId());
        servicoParoquias.enviarNotificacoes(paroquia.getId(), "Mensagem para todos", null);
        assertEquals(1, grupo1.getGerenciadorNotificacoes().getListaEntidades().size());
        assertEquals(1, grupo2.getGerenciadorNotificacoes().getListaEntidades().size());
    }

    @Test
    void enviarNotificacoesMensagemVaziaLancaExcecao() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        assertThrows(IllegalArgumentException.class,
            () -> servicoParoquias.enviarNotificacoes(paroquia.getId(), "", null));
        assertThrows(IllegalArgumentException.class,
            () -> servicoParoquias.enviarNotificacoes(paroquia.getId(), "   ", null));
        assertThrows(IllegalArgumentException.class,
            () -> servicoParoquias.enviarNotificacoes(paroquia.getId(), null, null));
    }

    @Test
    void enviarNotificacoesParaGrupoNaoVinculadoLancaExcecao() {
        Paroquia paroquia = servicoParoquias.criar("Paróquia", "123");
        Grupo grupo = servicoGrupos.criar("Grupo", "456");
        assertThrows(IllegalArgumentException.class,
            () -> servicoParoquias.enviarNotificacoes(paroquia.getId(), "Msg", List.of(grupo.getId())));
    }

    private static class ServicoGruposParaTeste extends ServicoGrupos {
        @Override
        public String salvar() {
            return "teste";
        }
    }
}

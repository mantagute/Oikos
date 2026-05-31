package oikos.service;

import oikos.domain.model.Grupo;
import oikos.domain.model.Evento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicoEventosTest {

    private ServicoGrupos servicoGrupos;
    private ServicoEventos servicoEventos;

    @BeforeEach
    void setUp() {
        servicoGrupos = new ServicoGruposParaTeste();
        servicoEventos = new ServicoEventos(servicoGrupos);
    }

    @Test
    void adicionarEventoValidoEmGrupo() {
        Grupo grupo = servicoGrupos.criarGrupo("Família", "1234");
        
        Evento evento = new Evento("Limpar o quarto", 15);
        servicoEventos.adicionar(grupo.getId(), evento);
        
        assertEquals(1, servicoEventos.getLista(grupo.getId()).size());
        assertEquals("Limpar o quarto", servicoEventos.getPorId(grupo.getId(), evento.getId()).getNome());
    }

    @Test
    void adicionarNomeVazioLancaExcecao() {
        Grupo grupo = servicoGrupos.criarGrupo("Família", "1234");
        
        Evento evento = new Evento("", 15);
        assertThrows(IllegalArgumentException.class, () -> servicoEventos.adicionar(grupo.getId(), evento));
    }

    @Test
    void adicionarPontosInvalidosLancaExcecao() {
        Grupo grupo = servicoGrupos.criarGrupo("Família", "1234");
        
        Evento evento = new Evento("Limpar o quarto", 0);
        assertThrows(IllegalArgumentException.class, () -> servicoEventos.adicionar(grupo.getId(), evento));
        
        Evento evento2 = new Evento("Limpar o quarto", -5);
        assertThrows(IllegalArgumentException.class, () -> servicoEventos.adicionar(grupo.getId(),evento2));
    }

    @Test
    void operacoesSemGrupoLancaExcecao() {
        Evento evento = new Evento("Leitura", 10);
        assertThrows(NoSuchElementException.class, () -> servicoEventos.adicionar(null, evento));
    }

    private static class ServicoGruposParaTeste extends ServicoGrupos {
        @Override
        public String salvar() {
            return "teste";
        }
    }
}

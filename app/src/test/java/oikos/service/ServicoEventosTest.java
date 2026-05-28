package oikos.service;

import oikos.domain.model.Evento;
import org.junit.jupiter.api.BeforeEach;
import oikos.util.HolderGrupoSelecionado;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicoEventosTest {

    private ServicoGrupos servicoGrupos;
    private ServicoEventos servicoEventos;

    @BeforeEach
    void setUp() {
        servicoGrupos = new ServicoGrupos(new HolderGrupoSelecionado());
        servicoEventos = new ServicoEventos(servicoGrupos);
    }

    @Test
    void adicionarEventoValidoDeveAdicionarNoGrupoAtivo() {
        servicoGrupos.criarGrupo("Família", "1234");
        
        Evento evento = new Evento("Limpar o quarto", 15);
        servicoEventos.adicionar(evento);
        
        assertEquals(1, servicoEventos.getLista().size());
        assertEquals("Limpar o quarto", servicoEventos.getPorId(evento.getId()).getNome());
    }

    @Test
    void adicionarNomeVazioDeveLancarExcecao() {
        servicoGrupos.criarGrupo("Família", "1234");
        
        Evento evento = new Evento("", 15);
        assertThrows(IllegalArgumentException.class, () -> servicoEventos.adicionar(evento));
    }

    @Test
    void adicionarPontosInvalidosDeveLancarExcecao() {
        servicoGrupos.criarGrupo("Família", "1234");
        
        Evento evento = new Evento("Limpar o quarto", 0);
        assertThrows(IllegalArgumentException.class, () -> servicoEventos.adicionar(evento));
        
        Evento evento2 = new Evento("Limpar o quarto", -5);
        assertThrows(IllegalArgumentException.class, () -> servicoEventos.adicionar(evento2));
    }

    @Test
    void operacoesSemGrupoSelecionadoDeveLancarExcecao() {
        Evento evento = new Evento("Leitura", 10);
        assertThrows(NoSuchElementException.class, () -> servicoEventos.adicionar(evento));
    }
}

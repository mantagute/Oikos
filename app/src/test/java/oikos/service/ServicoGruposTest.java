package oikos.service;

import oikos.domain.model.Grupo;
import oikos.domain.model.Pessoa;
import oikos.domain.model.Evento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicoGruposTest {

    private ServicoGrupos servicoGrupos;

    @BeforeEach
    void setUp() {
        servicoGrupos = new ServicoGruposParaTeste();
    }

    @Test
    void criarGrupoComDadosValidos() {
        Grupo grupo = servicoGrupos.criar("Família", "1234");
        
        assertNotNull(grupo.getId());
        assertEquals("Família", grupo.getNome());
        assertEquals(1, servicoGrupos.getLista().size());
    }

    @Test
    void criarGrupoComNomeOuSenhaVaziosLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> servicoGrupos.criar("", "1234"));
        assertThrows(IllegalArgumentException.class, () -> servicoGrupos.criar("Família", ""));
    }

    @Test
    void excluirGrupoComSenhaCorreta() {
        Grupo grupo = servicoGrupos.criar("Família", "1234");
        
        servicoGrupos.excluir(grupo.getId(), "1234");
        
        assertEquals(0, servicoGrupos.getLista().size());
    }

    @Test
    void excluirGrupoComSenhaIncorretaLancaExcecao() {
        Grupo grupo = servicoGrupos.criar("Família", "1234");
        
        assertThrows(SecurityException.class, () -> servicoGrupos.excluir(grupo.getId(), "senha-errada"));
        assertEquals(1, servicoGrupos.getLista().size()); 
    }

    @Test
    void pontuarComPessoaEEventoValidos() {
        Grupo grupo = servicoGrupos.criar("Família", "1234");
        
        Pessoa pessoa = new Pessoa("João");
        Evento evento = new Evento("Leitura", 10);
        grupo.getGerenciadorPessoas().adicionarEntidade(pessoa);
        grupo.getGerenciadorEventos().adicionarEntidade(evento);
        
        servicoGrupos.pontuar(grupo.getId(), pessoa.getId(), evento.getId());
        
        assertEquals(10, grupo.getPontuacaoAtual());
    }

    @Test
    void pontuarComPessoaOuEventoInvalidosLancaExcecao() {
        Grupo grupo = servicoGrupos.criar("Família", "1234");
        
        assertThrows(IllegalArgumentException.class, () -> 
            servicoGrupos.pontuar(grupo.getId(), UUID.randomUUID(), UUID.randomUUID())
        );
    }

    private static class ServicoGruposParaTeste extends ServicoGrupos {
        @Override
        public String salvar() {
            return "teste";
        }
    }
}

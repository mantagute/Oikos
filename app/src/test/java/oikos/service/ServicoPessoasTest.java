package oikos.service;

import oikos.domain.model.Grupo;
import oikos.domain.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServicoPessoasTest {

    private ServicoGrupos servicoGrupos;
    private ServicoPessoas servicoPessoas;

    @BeforeEach
    void setUp() {
        servicoGrupos = new ServicoGruposParaTeste();
        servicoPessoas = new ServicoPessoas(servicoGrupos);
    }

    @Test
    void adicionarPessoaComNomeCorreto() {
        Grupo grupo = servicoGrupos.criar("Família", "1234");
        
        Pessoa pessoa = new Pessoa("João");
        servicoPessoas.adicionar(grupo.getId(), pessoa);
        
        assertEquals(1, servicoPessoas.getLista(grupo.getId()).size());
        assertEquals("João", servicoPessoas.getPorId(grupo.getId(), pessoa.getId()).getNome());
        
        assertEquals(1, grupo.getGerenciadorPessoas().getListaEntidades().size());
    }

    @Test
    void adicionarComNomeVazioDeveLancaExcecao() {
        Grupo grupo = servicoGrupos.criar("Família", "1234");
        
        Pessoa pessoa = new Pessoa("   ");
        assertThrows(IllegalArgumentException.class, () -> servicoPessoas.adicionar(grupo.getId(), pessoa));
    }

    @Test
    void operacoesSemGrupoLancamExcecao() {
        Pessoa pessoa = new Pessoa("João");
        
        assertThrows(NoSuchElementException.class, () -> servicoPessoas.adicionar(null, pessoa));
        assertThrows(NoSuchElementException.class, () -> servicoPessoas.getLista(null));
    }

    @Test
    void removerPessoaDoGrupo() {
        Grupo grupo = servicoGrupos.criar("Família", "1234");
        Pessoa pessoa = new Pessoa("João");
        servicoPessoas.adicionar(grupo.getId(), pessoa);
        
        servicoPessoas.remover(grupo.getId(), pessoa.getId());
        
        assertTrue(servicoPessoas.getLista(grupo.getId()).isEmpty());
    }

    @Test
    void removerPessoaInexistenteLancaExcecao() {
        Grupo grupo = servicoGrupos.criar("Família", "1234");
        
        assertThrows(NoSuchElementException.class, () -> servicoPessoas.remover(grupo.getId(), UUID.randomUUID()));
    }

    private static class ServicoGruposParaTeste extends ServicoGrupos {
        @Override
        public String salvar() {
            return "teste";
        }
    }
}

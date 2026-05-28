package oikos.service;

import oikos.domain.model.Grupo;
import oikos.domain.model.Pessoa;
import oikos.util.HolderGrupoSelecionado;
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
        servicoGrupos = new ServicoGrupos(new HolderGrupoSelecionado());
        servicoPessoas = new ServicoPessoas(servicoGrupos);
    }

    @Test
    void adicionarComGrupoSelecionadoENomeValidoDeveAdicionarPessoaNoGrupoAtivo() {
        Grupo grupo = servicoGrupos.criarGrupo("Família", "1234");
        
        Pessoa pessoa = new Pessoa("João");
        servicoPessoas.adicionar(pessoa);
        
        assertEquals(1, servicoPessoas.getLista().size());
        assertEquals("João", servicoPessoas.getPorId(pessoa.getId()).getNome());
        
        assertEquals(1, grupo.getGerenciadorPessoas().getListaEntidades().size());
    }

    @Test
    void adicionarComNomeVazioDeveLancarExcecao() {
        servicoGrupos.criarGrupo("Família", "1234");
        
        Pessoa pessoa = new Pessoa("   ");
        assertThrows(IllegalArgumentException.class, () -> servicoPessoas.adicionar(pessoa));
    }

    @Test
    void operacoesSemGrupoSelecionadoDeveLancarExcecao() {
        Pessoa pessoa = new Pessoa("João");
        
        assertThrows(NoSuchElementException.class, () -> servicoPessoas.adicionar(pessoa));
        assertThrows(NoSuchElementException.class, () -> servicoPessoas.getLista());
    }

    @Test
    void removerPessoaExistenteDeveRemoverDoGrupo() {
        servicoGrupos.criarGrupo("Família", "1234");
        Pessoa pessoa = new Pessoa("João");
        servicoPessoas.adicionar(pessoa);
        
        servicoPessoas.remover(pessoa.getId());
        
        assertTrue(servicoPessoas.getLista().isEmpty());
    }

    @Test
    void removerPessoaInexistenteDeveLancarExcecao() {
        servicoGrupos.criarGrupo("Família", "1234");
        
        assertThrows(NoSuchElementException.class, () -> servicoPessoas.remover(UUID.randomUUID()));
    }
}

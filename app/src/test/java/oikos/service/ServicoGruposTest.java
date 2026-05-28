package oikos.service;

import oikos.domain.model.Grupo;
import oikos.domain.model.Pessoa;
import oikos.domain.model.Evento;
import oikos.util.HolderGrupoSelecionado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicoGruposTest {

    private ServicoGrupos servicoGrupos;

    @BeforeEach
    void setUp() {
        servicoGrupos = new ServicoGrupos(new HolderGrupoSelecionado());
    }

    @Test
    void criarGrupoComDadosValidosDeveCriarESelecionar() {
        Grupo grupo = servicoGrupos.criarGrupo("Família", "1234");
        
        assertNotNull(grupo.getId());
        assertEquals("Família", grupo.getNome());
        assertEquals(1, servicoGrupos.getListaGrupos().size());
        
        assertEquals(grupo, servicoGrupos.getGrupoSelecionado());
    }

    @Test
    void criarGrupoComNomeOuSenhaVaziosDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> servicoGrupos.criarGrupo("", "1234"));
        assertThrows(IllegalArgumentException.class, () -> servicoGrupos.criarGrupo("Família", ""));
    }

    @Test
    void selecionarGrupoComIdInvalidoDeveLancarExcecao() {
        assertThrows(NoSuchElementException.class, () -> servicoGrupos.selecionarGrupo(UUID.randomUUID()));
    }

    @Test
    void excluirGrupoComSenhaCorretaDeveRemoverELimparSelecao() {
        Grupo grupo = servicoGrupos.criarGrupo("Família", "1234");
        
        servicoGrupos.excluirGrupo(grupo.getId(), "1234");
        
        assertEquals(0, servicoGrupos.getListaGrupos().size());
        assertThrows(NoSuchElementException.class, () -> servicoGrupos.getGrupoSelecionado());
    }

    @Test
    void excluirGrupoComSenhaIncorretaDeveLancarExcecao() {
        Grupo grupo = servicoGrupos.criarGrupo("Família", "1234");
        
        assertThrows(SecurityException.class, () -> servicoGrupos.excluirGrupo(grupo.getId(), "senha-errada"));
        assertEquals(1, servicoGrupos.getListaGrupos().size()); 
    }

    @Test
    void pontuarComPessoaEEventoValidosDeveAcumularPontosNoGrupo() {
        Grupo grupo = servicoGrupos.criarGrupo("Família", "1234");
        
        Pessoa pessoa = new Pessoa("João");
        Evento evento = new Evento("Leitura", 10);
        grupo.getGerenciadorPessoas().adicionarEntidade(pessoa);
        grupo.getGerenciadorEventos().adicionarEntidade(evento);
        
        servicoGrupos.pontuar(pessoa.getId(), evento.getId());
        
        assertEquals(10, grupo.getPontuacaoAtual());
    }

    @Test
    void pontuarComPessoaOuEventoInvalidosDeveLancarExcecao() {
        servicoGrupos.criarGrupo("Família", "1234");
        
        assertThrows(IllegalArgumentException.class, () -> 
            servicoGrupos.pontuar(UUID.randomUUID(), UUID.randomUUID())
        );
    }
}

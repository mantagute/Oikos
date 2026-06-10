package oikos.service;

import oikos.domain.interfaces.Autenticavel;
import oikos.domain.model.Entidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServicoEscopoMaiorTest {

    private ServicoEscopoMaior<EntidadeTeste> servico;

    @BeforeEach
    void setUp() {
        servico = new ServicoEscopoMaior<EntidadeTeste>(null) {
            @Override
            protected EntidadeTeste instanciar(String nome, String senha) {
                return new EntidadeTeste(nome, senha);
            }

            @Override
            protected String getNome(EntidadeTeste entidade) {
                return entidade.getNome();
            }

            @Override
            public String salvar() {
                return "teste";
            }
        };
    }

    @Test
    void criarComDadosValidos() {
        EntidadeTeste entidade = servico.criar("Teste", "1234");
        assertNotNull(entidade.getId());
        assertEquals("Teste", entidade.getNome());
        assertEquals(1, servico.getLista().size());
    }

    @Test
    void criarComNomeVazioLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> servico.criar("", "1234"));
        assertThrows(IllegalArgumentException.class, () -> servico.criar("   ", "1234"));
    }

    @Test
    void criarComSenhaVaziaLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> servico.criar("Teste", ""));
    }

    @Test
    void criarComNomeDuplicadoLancaExcecao() {
        servico.criar("Teste", "1234");
        assertThrows(IllegalArgumentException.class, () -> servico.criar("teste", "5678"));
    }

    @Test
    void getPorIdRetornaEntidadeExistente() {
        EntidadeTeste criada = servico.criar("Teste", "1234");
        EntidadeTeste encontrada = servico.getPorId(criada.getId());
        assertEquals(criada.getId(), encontrada.getId());
    }

    @Test
    void getPorIdComIdInexistenteLancaExcecao() {
        assertThrows(NoSuchElementException.class, () -> servico.getPorId(UUID.randomUUID()));
    }

    @Test
    void excluirComSenhaCorreta() {
        EntidadeTeste entidade = servico.criar("Teste", "1234");
        servico.excluir(entidade.getId(), "1234");
        assertTrue(servico.getLista().isEmpty());
    }

    @Test
    void excluirComSenhaIncorreta() {
        EntidadeTeste entidade = servico.criar("Teste", "1234");
        assertThrows(SecurityException.class, () -> servico.excluir(entidade.getId(), "senha-errada"));
        assertEquals(1, servico.getLista().size());
    }

    private static class EntidadeTeste extends Entidade implements Autenticavel {
        private final String nome;
        private final String senha;

        EntidadeTeste(String nome, String senha) {
            this.nome = nome;
            this.senha = senha;
        }

        String getNome() { return nome; }

        @Override
        public boolean autenticarSenha(String senha) {
            return this.senha.equals(senha);
        }
    }
}

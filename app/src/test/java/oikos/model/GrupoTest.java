package oikos.model;

import oikos.domain.model.Grupo;
import oikos.domain.model.Pessoa;
import oikos.domain.model.Evento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GrupoTest {
    @Test
    public void AutenticacaoAdequada() {
        Grupo grupo = new Grupo("OSS", "senha123");
        assert grupo.autenticarSenha("senha123");
    }

    @Test
    public void AutenticacaoInadequada() {
        Grupo grupo = new Grupo("OSS", "senha123");
        assert !grupo.autenticarSenha("senhaErrada");
    }

    @Test 
    public void RedefinirSenhaFalho() {
        Grupo grupo = new Grupo("OSS", "bozo");
        assertThrows(IllegalArgumentException.class, () -> {
            grupo.redefinirSenha("castro", "midon");
        });
    }

    @Test
    public void RedefinirSenhaSucesso() {
        Grupo grupo = new Grupo("OSS", "bozo");
        grupo.redefinirSenha("bozo", "castro");
        assert grupo.autenticarSenha("castro");
    }

    @Test
    public void AddPontosSemBaterMeta() {
        Grupo grupo = new Grupo("OSS", "senha");
        grupo.adicionarPontos(500);
        assert grupo.getPontuacaoAtual() == 500;
        assert grupo.getMetasBatidas() == 0;
    }

    @Test
    public void AddPontosBateMeta() {
        Grupo grupo = new Grupo("oss", "senha");
        grupo.adicionarPontos(1000);
        assert grupo.getPontuacaoAtual() == 0;
        assert grupo.getMetasBatidas() == 1;
    }

    @Test
    public void AddPontosPassaPontuacaoMaxima() {
        Grupo grupo = new Grupo("oss", "senha");
        grupo.adicionarPontos(1500);
        assert grupo.getPontuacaoAtual() == 500;
        assert grupo.getMetasBatidas() == 1;
    }

    @Test 
    public void PontuaSePessoaeEventoNoGrupo() {
        Grupo grupo = new Grupo("OSS", "senha");
        Pessoa pessoa = new Pessoa("BOZO");
        Evento evento = new Evento("Evento Teste", 200);
        grupo.getGerenciadorPessoas().adicionarEntidade(pessoa);
        grupo.getGerenciadorEventos().adicionarEntidade(evento);
        grupo.pontuar(pessoa, evento);
        assert grupo.getPontuacaoAtual() == 200;
    }

    @Test
    public void NaoPontuaSePessoaNaoEstaNoGrupo() {
        Grupo grupo = new Grupo("OSS", "senha");
        Pessoa pessoa = new Pessoa("BOZO");
        Evento evento = new Evento("Evento Teste", 200);
        grupo.getGerenciadorEventos().adicionarEntidade(evento);
        
        assertThrows(IllegalArgumentException.class, () -> {
            grupo.pontuar(pessoa, evento);
        });
    }
}

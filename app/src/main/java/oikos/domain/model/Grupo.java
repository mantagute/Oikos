package oikos.domain.model;

import oikos.domain.interfaces.Autenticavel;
import oikos.domain.interfaces.Pontuavel;
import oikos.domain.manager.GerenciadorEventos;
import oikos.domain.manager.GerenciadorPessoas;

public class Grupo implements Pontuavel, Autenticavel {
    private String nome;
    private String senha;
    private GerenciadorEventos gerenciadorEventos;
    private GerenciadorPessoas gerenciadorPessoas;
    private int metaPontos;
    private int pontuacaoAtual;
    private int metasBatidas;
    
    public Grupo(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.gerenciadorEventos = new GerenciadorEventos(this);
        this.gerenciadorPessoas = new GerenciadorPessoas(this);
        this.metaPontos = 1000; // Meta padrão, pode ser ajustada
        this.pontuacaoAtual = 0;
        this.metasBatidas = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getMeta() {
        return metaPontos;
    }

    public int getPontuacaoAtual() {
        return pontuacaoAtual;
    }

    public int getMetasBatidas() {
        return metasBatidas;
    }

    public GerenciadorEventos getGerenciadorEventos() {
        return gerenciadorEventos;
    }

    public GerenciadorPessoas getGerenciadorPessoas() {
        return gerenciadorPessoas;
    }

    public void redefinirMeta(int novaMeta) {
        this.metaPontos = novaMeta;
    }

    public void reiniciarPontos() {
        this.pontuacaoAtual = 0;
    }

    public boolean autenticarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    public void redefinirSenha(String supostaSenhaAtual, String novaSenha) {
        if (this.senha.equals(supostaSenhaAtual)) {
            this.senha = novaSenha;
        }
        else {
            throw new IllegalArgumentException("Senha atual incorreta. Não foi possível redefinir a senha.");
        }
    }

    public void adicionarPontos(int pontos){
        this.pontuacaoAtual = this.pontuacaoAtual + pontos;
        if (this.pontuacaoAtual >= this.metaPontos) {
            this.metasBatidas++;
            this.pontuacaoAtual = 0;
        }
    }

    public void pontuar(Pessoa pessoa, Evento evento) {
        Pessoa membro = gerenciadorPessoas.getPorId(pessoa.getId());
        if (membro == null) {
            throw new IllegalArgumentException("Pessoa não pertence a este grupo.");
        }
        Evento acao = gerenciadorEventos.getPorId(evento.getId());
        if (acao == null) {
            throw new IllegalArgumentException("Evento não pertence a este grupo.");
        }
        this.adicionarPontos(acao.getPontos());
    }
}

package oikos.domain.model;

import oikos.domain.interfaces.Autenticavel;
import oikos.domain.interfaces.Classificavel;
import oikos.domain.interfaces.Pontuavel;
import oikos.domain.manager.GerenciadorEventos;
import oikos.domain.manager.GerenciadorPessoas;

/**
 * Representa um Grupo no sistema Oikos.
 * É a entidade central (Aggregate Root) que gerencia pessoas, eventos e metas.
 * Implementa as lógicas de pontuação e autenticação do grupo.
 */
public class Grupo extends Entidade implements Pontuavel, Autenticavel, Classificavel{
    private String nome;
    private String senha;
    private GerenciadorEventos gerenciadorEventos;
    private GerenciadorPessoas gerenciadorPessoas;
    private int metaPontos;
    private int pontuacaoAtual;
    private int metasBatidas;
    
    /**
     * Cria um novo Grupo inicializando seus gerenciadores e placares com valores padrão.
     * @param nome Nome do grupo.
     * @param senha Senha de acesso do grupo.
     */
    public Grupo(String nome, String senha) {
        super();
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

    @Override
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

    public String getClassificacao() {
        return classificar(metasBatidas);
    }

    /**
     * Altera a quantidade de pontos necessários para bater uma meta.
     * @param novaMeta Novo valor da meta.
     */
    public void redefinirMeta(int novaMeta) {
        this.metaPontos = novaMeta;
    }

    /**
     * Zera o placar de pontuação atual do grupo.
     */
    public void reiniciarPontos() {
        this.pontuacaoAtual = 0;
    }

    /**
     * Valida se a senha informada corresponde à senha do grupo.
     * @param senhaInformada Senha a ser testada.
     * @return true se a senha estiver correta, false caso contrário.
     */
    public boolean autenticarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    /**
     * Troca a senha do grupo mediante validação da senha antiga.
     * @param supostaSenhaAtual Senha atual para verificação.
     * @param novaSenha Nova senha desejada.
     * @throws IllegalArgumentException se a senha atual estiver incorreta.
     */
    public void redefinirSenha(String supostaSenhaAtual, String novaSenha) {
        if (this.senha.equals(supostaSenhaAtual)) {
            this.senha = novaSenha;
        }
        else {
            throw new IllegalArgumentException("Senha atual incorreta. Não foi possível redefinir a senha.");
        }
    }

    /**
     * Adiciona pontos ao grupo. Se o acumulado ultrapassar a meta atual, 
     * incrementa o contador de metas batidas e retém o saldo de pontos excedente.
     * @param pontos Quantidade de pontos a adicionar.
     */
    public void adicionarPontos(int pontos){
        this.pontuacaoAtual = this.pontuacaoAtual + pontos;
        while (this.pontuacaoAtual >= this.metaPontos) {
            this.metasBatidas++;
            this.pontuacaoAtual = this.pontuacaoAtual - this.metaPontos;
        }
    }

    /**
     * Registra a realização de um evento por um membro e pontua o grupo.
     * Delega a lógica matemática de acúmulo de pontos para o método adicionarPontos().
     * @param pessoa Membro do grupo que realizou a ação.
     * @param evento Evento realizado.
     * @throws IllegalArgumentException se a pessoa ou o evento não existirem neste grupo.
     */
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

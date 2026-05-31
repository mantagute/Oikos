package oikos.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import oikos.domain.interfaces.Autenticavel;
import oikos.domain.interfaces.Classificavel;
import oikos.domain.interfaces.Pontuavel;
import oikos.domain.manager.Gerenciador;

/**
 * Representa um Grupo no sistema Oikos.
 * É a entidade central (Aggregate Root) que gerencia pessoas, eventos e metas.
 * Implementa as lógicas de pontuação e autenticação do grupo.
 */
public class Grupo extends Entidade implements Pontuavel, Autenticavel, Classificavel{
    private String nome;
    @JsonProperty("senha")
    private String senha;
    private Gerenciador<Evento> gerenciadorEventos;
    private Gerenciador<Pessoa> gerenciadorPessoas;
    private int meta;
    private int pontuacaoAtual;
    private int metasBatidas;
    
    /**
     * Cria um novo Grupo inicializando seus gerenciadores e placares com valores padrão.
     * @param nome Nome do grupo.
     * @param senha Senha de acesso do grupo.
     */
    @JsonCreator
    public Grupo(@JsonProperty("nome") String nome,@JsonProperty("senha") String senha) {
        super();
        this.nome = nome;
        this.senha = senha;
        this.gerenciadorEventos = new Gerenciador<>(this);
        this.gerenciadorPessoas = new Gerenciador<>(this);
        this.meta = 1000; // Meta padrão, pode ser ajustada
        this.pontuacaoAtual = 0;
        this.metasBatidas = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMeta() {
        return meta;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    @Override
    public int getPontuacaoAtual() {
        return pontuacaoAtual;
    }
    public void setPontuacaoAtual(int valor) {
        this.pontuacaoAtual = valor;
    }

    public int getMetasBatidas() {
        return metasBatidas;
    }
    public void setMetasBatidas(int valor) {
        this.metasBatidas = valor;
    }

    public Gerenciador<Evento> getGerenciadorEventos() {
        return gerenciadorEventos;
    }

    public void setGerenciadorEventos(Gerenciador<Evento> gerenciadorEventos) {
        this.gerenciadorEventos = gerenciadorEventos;
    }

    public Gerenciador<Pessoa> getGerenciadorPessoas() {
        return gerenciadorPessoas;
    }

    public void setGerenciadorPessoas(Gerenciador<Pessoa> gerenciadorPessoas) {
        this.gerenciadorPessoas = gerenciadorPessoas;
    }

    public String getClassificacao() {
        return classificar(metasBatidas);
    }

    /**
     * Altera a quantidade de pontos necessários para bater uma meta.
     * @param novaMeta Novo valor da meta.
     */
    public void redefinirMeta(int novaMeta) {
        this.meta = novaMeta;
    }

    /**
     * Zera o placar de pontuação atual do grupo.
     */
    @Override
    public void reiniciarPontos() {
        this.pontuacaoAtual = 0;
    }

    /**
     * Valida se a senha informada corresponde à senha do grupo.
     * @param senhaInformada Senha a ser testada.
     * @return true se a senha estiver correta, false caso contrário.
     */
    @Override
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
    @Override
    public void adicionarPontos(int pontos){
        this.pontuacaoAtual = this.pontuacaoAtual + pontos;
        while (this.pontuacaoAtual >= this.meta) {
            this.metasBatidas++;
            this.pontuacaoAtual = this.pontuacaoAtual - this.meta;
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

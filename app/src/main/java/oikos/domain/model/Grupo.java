package oikos.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import oikos.domain.interfaces.Autenticavel;
import oikos.domain.interfaces.Classificavel;
import oikos.domain.interfaces.Pontuavel;
import oikos.domain.manager.Gerenciador;

/**
 * Representa um Grupo no sistema Oikos.
 * É a entidade central (Aggregate Root) que gerencia pessoas, eventos,
 * notificações e metas. Implementa as lógicas de pontuação, autenticação
 * e classificação do grupo.
 */
public class Grupo extends Entidade implements Pontuavel, Autenticavel, Classificavel {
    private String nome;
    @JsonProperty("senha")
    private String senha;
    private Gerenciador<Evento> gerenciadorEventos;
    private Gerenciador<Pessoa> gerenciadorPessoas;
    private Gerenciador<Notificacao> gerenciadorNotificacoes;
    private int meta;
    private int pontuacaoAtual;
    private int metasBatidas;

    /**
     * Cria um novo Grupo inicializando seus gerenciadores e placares com valores padrão.
     * A meta padrão é definida em 1000 pontos.
     *
     * @param nome  Nome do grupo.
     * @param senha Senha de acesso do grupo.
     */
    @JsonCreator
    public Grupo(@JsonProperty("nome") String nome, @JsonProperty("senha") String senha) {
        super();
        this.nome = nome;
        this.senha = senha;
        this.gerenciadorEventos = new Gerenciador<>(this);
        this.gerenciadorPessoas = new Gerenciador<>(this);
        this.gerenciadorNotificacoes = new Gerenciador<>(this);
        this.meta = 1000;
        this.pontuacaoAtual = 0;
        this.metasBatidas = 0;
    }

    /**
     * Retorna o nome do grupo.
     *
     * @return Nome do grupo como {@code String}.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome do grupo.
     *
     * @param nome Novo nome do grupo.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o valor atual da meta de pontuação do grupo.
     *
     * @return Meta atual como {@code int}.
     */
    public int getMeta() {
        return meta;
    }

    /**
     * Atualiza a senha do grupo diretamente.
     *
     * @param senha Nova senha do grupo.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Atualiza o valor da meta diretamente.
     *
     * @param meta Novo valor da meta.
     */
    public void setMeta(int meta) {
        this.meta = meta;
    }

    /**
     * Retorna a pontuação acumulada no ciclo atual.
     *
     * @return Pontuação atual como {@code int}.
     */
    @Override
    public int getPontuacaoAtual() {
        return pontuacaoAtual;
    }

    /**
     * Define a pontuação atual do grupo diretamente.
     *
     * @param valor Novo valor da pontuação.
     */
    public void setPontuacaoAtual(int valor) {
        this.pontuacaoAtual = valor;
    }

    /**
     * Retorna o número total de metas batidas pelo grupo desde sua criação.
     *
     * @return Total de metas batidas como {@code int}.
     */
    public int getMetasBatidas() {
        return metasBatidas;
    }

    /**
     * Define o número de metas batidas diretamente.
     *
     * @param valor Novo valor do contador de metas batidas.
     */
    public void setMetasBatidas(int valor) {
        this.metasBatidas = valor;
    }

    /**
     * Retorna o gerenciador de eventos do grupo.
     *
     * @return {@link Gerenciador} de {@link Evento}.
     */
    public Gerenciador<Evento> getGerenciadorEventos() {
        return gerenciadorEventos;
    }

    /**
     * Define o gerenciador de eventos. Usado pelo Jackson na desserialização.
     *
     * @param gerenciadorEventos Novo gerenciador de eventos.
     */
    public void setGerenciadorEventos(Gerenciador<Evento> gerenciadorEventos) {
        this.gerenciadorEventos = gerenciadorEventos;
    }

    /**
     * Retorna o gerenciador de pessoas do grupo.
     *
     * @return {@link Gerenciador} de {@link Pessoa}.
     */
    public Gerenciador<Pessoa> getGerenciadorPessoas() {
        return gerenciadorPessoas;
    }

    /**
     * Define o gerenciador de pessoas. Usado pelo Jackson na desserialização.
     *
     * @param gerenciadorPessoas Novo gerenciador de pessoas.
     */
    public void setGerenciadorPessoas(Gerenciador<Pessoa> gerenciadorPessoas) {
        this.gerenciadorPessoas = gerenciadorPessoas;
    }

    /**
     * Retorna o gerenciador de notificações do grupo.
     *
     * @return {@link Gerenciador} de {@link Notificacao}.
     */
    public Gerenciador<Notificacao> getGerenciadorNotificacoes() {
        return gerenciadorNotificacoes;
    }

    /**
     * Define o gerenciador de notificações. Usado pelo Jackson na desserialização.
     *
     * @param gerenciadorNotificacoes Novo gerenciador de notificações.
     */
    public void setGerenciadorNotificacoes(Gerenciador<Notificacao> gerenciadorNotificacoes) {
        this.gerenciadorNotificacoes = gerenciadorNotificacoes;
    }

    /**
     * Retorna a classificação atual do grupo com base no número de metas batidas.
     *
     * @return Classificação como {@code String} (ex: "Bronze", "Ouro").
     */
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
     * @return {@code true} se a senha estiver correta, {@code false} caso contrário.
     */
    @Override
    public boolean autenticarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    /**
     * Troca a senha do grupo mediante validação da senha antiga.
     * @param supostaSenhaAtual Senha atual para verificação.
     * @param novaSenha         Nova senha desejada.
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
     * Delega a lógica matemática de acúmulo de pontos para {@link #adicionarPontos(int)}.
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

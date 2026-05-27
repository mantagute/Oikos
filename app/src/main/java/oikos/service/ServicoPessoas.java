package oikos.service;

import oikos.domain.model.Pessoa;
import oikos.domain.model.Grupo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Serviço responsável pelas operações de gerenciamento de pessoas dentro de um Grupo.
 * <p>
 * Atua como camada de validação de regras de negócio, delegando as operações
 * de coleção ao {@link oikos.domain.manager.GerenciadorPessoas} do grupo.
 * </p>
 */
public class ServicoPessoas {

    /**
     * Cria e adiciona uma nova pessoa ao grupo informado.
     * <p>
     * Pessoas diferentes podem ter o mesmo nome — a unicidade é garantida
     * pelo UUID gerado automaticamente em {@link oikos.domain.model.Entidade}.
     * </p>
     *
     * @param grupo o grupo ao qual a pessoa será adicionada
     * @param nome  nome da pessoa (não pode ser vazio)
     * @return a {@link Pessoa} criada e adicionada ao grupo
     * @throws IllegalArgumentException se o nome for vazio ou em branco
     */
    public Pessoa adicionar(Grupo grupo, String nome) {
        if (nome.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio");

        Pessoa nova = new Pessoa(nome);
        grupo.getGerenciadorPessoas().adicionarEntidade(nova);
        return nova;
    }

    /**
     * Remove uma pessoa do grupo com base em seu UUID.
     *
     * @param grupo o grupo do qual a pessoa será removida
     * @param id    identificador único da pessoa a ser removida
     * @throws NoSuchElementException se nenhuma pessoa com o ID informado existir no grupo
     */
    public void remover(Grupo grupo, UUID id) {
        if (grupo.getGerenciadorPessoas().getPorId(id) == null)
            throw new NoSuchElementException("Pessoa não encontrada");

        grupo.getGerenciadorPessoas().removerEntidade(id);
    }

    /**
     * Retorna a lista de todas as pessoas vinculadas ao grupo.
     *
     * @param grupo o grupo cujas pessoas serão listadas
     * @return lista de {@link Pessoa} pertencentes ao grupo
     */
    public List<Pessoa> listar(Grupo grupo) {
        return grupo.getGerenciadorPessoas().getListaEntidades();
    }

    /**
     * Busca uma pessoa pelo seu UUID dentro do grupo.
     *
     * @param grupo o grupo onde a busca será realizada
     * @param id    identificador único da pessoa
     * @return a {@link Pessoa} correspondente ao ID informado
     * @throws NoSuchElementException se nenhuma pessoa com o ID informado existir no grupo
     */
    public Pessoa buscarPorId(Grupo grupo, UUID id) {
        Pessoa pessoa = grupo.getGerenciadorPessoas().getPorId(id);
        if (pessoa == null)
            throw new NoSuchElementException("Pessoa não encontrada");
        return pessoa;
    }
}

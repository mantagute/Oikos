package oikos.service;

import oikos.domain.model.Evento;
import oikos.domain.model.Grupo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Serviço responsável pelas operações de gerenciamento de eventos dentro de um Grupo.
 * <p>
 * Atua como camada de validação de regras de negócio, delegando as operações
 * de coleção ao {@link oikos.domain.manager.GerenciadorEventos} do grupo.
 * </p>
 */
public class ServicoEventos {

    /**
     * Cria e adiciona um novo evento ao grupo informado.
     *
     * @param grupo  o grupo ao qual o evento será adicionado
     * @param nome   nome descritivo do evento (não pode ser vazio)
     * @param pontos valor de pontos que o evento concede ao ser realizado (mínimo 1)
     * @return o {@link Evento} criado e adicionado ao grupo
     * @throws IllegalArgumentException se o nome for vazio ou os pontos forem menores que 1
     */
    public Evento adicionar(Grupo grupo, String nome, int pontos) {
        if (nome.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio");
        if (pontos < 1)
            throw new IllegalArgumentException("Pontos deve ser maior que zero");

        Evento novo = new Evento(nome, pontos);
        grupo.getGerenciadorEventos().adicionarEntidade(novo);
        return novo;
    }

    /**
     * Remove um evento do grupo com base em seu UUID.
     *
     * @param grupo o grupo do qual o evento será removido
     * @param id    identificador único do evento a ser removido
     * @throws NoSuchElementException se nenhum evento com o ID informado existir no grupo
     */
    public void remover(Grupo grupo, UUID id) {
        if (grupo.getGerenciadorEventos().getPorId(id) == null)
            throw new NoSuchElementException("Evento não encontrado");

        grupo.getGerenciadorEventos().removerEntidade(id);
    }

    /**
     * Retorna a lista de todos os eventos cadastrados no grupo.
     *
     * @param grupo o grupo cujos eventos serão listados
     * @return lista de {@link Evento} pertencentes ao grupo
     */
    public List<Evento> listar(Grupo grupo) {
        return grupo.getGerenciadorEventos().getListaEntidades();
    }

    /**
     * Busca um evento pelo seu UUID dentro do grupo.
     *
     * @param grupo o grupo onde a busca será realizada
     * @param id    identificador único do evento
     * @return o {@link Evento} correspondente ao ID informado
     * @throws NoSuchElementException se nenhum evento com o ID informado existir no grupo
     */
    public Evento buscarPorId(Grupo grupo, UUID id) {
        Evento evento = grupo.getGerenciadorEventos().getPorId(id);
        if (evento == null)
            throw new NoSuchElementException("Evento não encontrado");
        return evento;
    }
}

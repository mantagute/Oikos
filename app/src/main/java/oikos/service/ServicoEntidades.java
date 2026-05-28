package oikos.service;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Entidade;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Serviço genérico que encapsula operações CRUD comuns sobre entidades do domínio,
 * operando sempre sobre o grupo atualmente selecionado.
 *
 * @param <TipoEntidade> O tipo de entidade gerenciada (deve estender Entidade).
 */
public abstract class ServicoEntidades<TipoEntidade extends Entidade> {

    protected final ServicoGrupos servicoGrupos;

    /**
     * Cria o serviço vinculado ao gerenciador de grupos.
     *
     * @param servicoGrupos O serviço de grupos, que mantém o grupo ativo.
     */
    public ServicoEntidades(ServicoGrupos servicoGrupos) {
        this.servicoGrupos = servicoGrupos;
    }

    /**
     * Obtém o gerenciador correspondente do grupo atualmente ativo.
     * Subclasses devem implementar este método para retornar o gerenciador correto (pessoas, eventos, etc).
     */
    protected abstract Gerenciador<TipoEntidade> getGerenciadorAtual();

    /**
     * Retorna a lista de todas as entidades gerenciadas no grupo ativo.
     *
     * @return Lista com todas as entidades.
     */
    public List<TipoEntidade> getLista() {
        return getGerenciadorAtual().getListaEntidades();
    }

    /**
     * Busca uma entidade pelo seu UUID no grupo ativo.
     *
     * @param id UUID da entidade.
     * @return A entidade encontrada.
     * @throws NoSuchElementException se nenhuma entidade com o ID informado existir no grupo ativo.
     */
    public TipoEntidade getPorId(UUID id) {
        TipoEntidade entidade = getGerenciadorAtual().getPorId(id);
        if (entidade == null) {
            throw new NoSuchElementException("Entidade não encontrada com o id: " + id);
        }
        return entidade;
    }

    /**
     * Adiciona uma entidade à coleção do grupo ativo.
     * Subclasses devem sobrescrever este método para aplicar validações específicas.
     *
     * @param entidade A entidade a ser adicionada.
     */
    public abstract void adicionar(TipoEntidade entidade);

    /**
     * Remove uma entidade da coleção do grupo ativo pelo seu UUID.
     *
     * @param id UUID da entidade a ser removida.
     * @throws NoSuchElementException se nenhuma entidade com o ID informado existir no grupo ativo.
     */
    public void remover(UUID id) {
        getPorId(id); // valida existência antes de remover
        getGerenciadorAtual().removerEntidade(id);
    }
}

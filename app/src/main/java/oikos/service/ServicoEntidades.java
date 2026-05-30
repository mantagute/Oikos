package oikos.service;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Entidade;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Serviço genérico que encapsula operações CRUD comuns sobre entidades do domínio,
 * operando sobre as entidades vinculadas a um grupo específico.
 *
 * @param <TipoEntidade> O tipo de entidade gerenciada (deve estender Entidade).
 */
public abstract class ServicoEntidades<TipoEntidade extends Entidade> {

    protected final ServicoGrupos servicoGrupos;

    /**
     * Cria o serviço vinculado ao gerenciador de grupos.
     *
     * @param servicoGrupos O serviço de grupos, usado para localizar o grupo.
     */
    public ServicoEntidades(ServicoGrupos servicoGrupos) {
        this.servicoGrupos = servicoGrupos;
    }

    /**
     * Obtém o gerenciador correspondente do grupo informado.
     * Subclasses devem implementar este método para retornar o gerenciador correto (pessoas, eventos, etc).
     */
    protected abstract Gerenciador<TipoEntidade> getGerenciadorPorGrupoId(UUID grupoId);

    /**
     * Retorna a lista de todas as entidades gerenciadas no grupo informado.
     *
     * @return Lista com todas as entidades.
     */
    public List<TipoEntidade> getLista(UUID grupoId) {
        Gerenciador<TipoEntidade> gerenciador = getGerenciadorPorGrupoId(grupoId);
        List<TipoEntidade> entidades = gerenciador.getListaEntidades();
        return entidades;
    }

    /**
     * Busca uma entidade pelo seu UUID no grupo informado.
     *
     * @param grupoId    UUID do grupo.
     * @param entidadeId UUID da entidade.
     * @return A entidade encontrada.
     * @throws NoSuchElementException se nenhuma entidade com o ID informado existir no grupo.
     */
    public TipoEntidade getPorId(UUID grupoId, UUID entidadeId) {
        TipoEntidade entidade = getEntidadeObrigatoria(grupoId, entidadeId);
        return entidade;
    }

    /**
     * Adiciona uma entidade à coleção do grupo informado.
     * Subclasses devem sobrescrever este método para aplicar validações específicas.
     *
     * @param grupoId  UUID do grupo.
     * @param entidade A entidade a ser adicionada.
     */
    public abstract void adicionar(UUID grupoId, TipoEntidade entidade);

    /**
     * Remove uma entidade da coleção do grupo informado pelo seu UUID.
     *
     * @param grupoId    UUID do grupo.
     * @param entidadeId UUID da entidade a ser removida.
     * @throws NoSuchElementException se nenhuma entidade com o ID informado existir no grupo.
     */
    public void remover(UUID grupoId, UUID entidadeId) {
        getEntidadeObrigatoria(grupoId, entidadeId);

        Gerenciador<TipoEntidade> gerenciador = getGerenciadorPorGrupoId(grupoId);
        gerenciador.removerEntidade(entidadeId);

        servicoGrupos.salvar();
    }

    /**
     * Busca uma entidade e garante que ela exista no grupo informado.
     *
     * @param grupoId    UUID do grupo.
     * @param entidadeId UUID da entidade.
     * @return a entidade encontrada.
     * @throws NoSuchElementException se nenhuma entidade com o ID informado existir no grupo.
     */
    private TipoEntidade getEntidadeObrigatoria(UUID grupoId, UUID entidadeId) {
        Gerenciador<TipoEntidade> gerenciador = getGerenciadorPorGrupoId(grupoId);
        TipoEntidade entidade = gerenciador.getPorId(entidadeId);
        if (entidade == null) {
            throw new NoSuchElementException("Entidade não encontrada com o id: " + entidadeId);
        }

        return entidade;
    }
}

package oikos.service;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Entidade;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Serviço genérico que encapsula operações CRUD comuns sobre entidades do domínio,
 * adicionando validação e tratamento de exceções sobre o Gerenciador.
 *
 * @param <TipoEntidade> O tipo de entidade gerenciada (deve estender Entidade).
 */
public abstract class ServicoEntidades<TipoEntidade extends Entidade> {

    protected final Gerenciador<TipoEntidade> gerenciador;

    /**
     * Cria o serviço vinculado a um gerenciador específico.
     *
     * @param gerenciador O gerenciador que mantém a coleção de entidades.
     */
    public ServicoEntidades(Gerenciador<TipoEntidade> gerenciador) {
        this.gerenciador = gerenciador;
    }

    /**
     * Retorna a lista de todas as entidades gerenciadas.
     *
     * @return Lista com todas as entidades.
     */
    public List<TipoEntidade> getLista() {
        return gerenciador.getListaEntidades();
    }

    /**
     * Busca uma entidade pelo seu UUID.
     *
     * @param id UUID da entidade.
     * @return A entidade encontrada.
     * @throws NoSuchElementException se nenhuma entidade com o ID informado existir.
     */
    public TipoEntidade getPorId(UUID id) {
        TipoEntidade entidade = gerenciador.getPorId(id);
        if (entidade == null) {
            throw new NoSuchElementException("Entidade não encontrada com o id: " + id);
        }
        return entidade;
    }

    /**
     * Adiciona uma entidade à coleção.
     * Subclasses devem sobrescrever este método para aplicar validações específicas.
     *
     * @param entidade A entidade a ser adicionada.
     */
    public abstract void adicionar(TipoEntidade entidade);

    /**
     * Remove uma entidade da coleção pelo seu UUID.
     *
     * @param id UUID da entidade a ser removida.
     * @throws NoSuchElementException se nenhuma entidade com o ID informado existir.
     */
    public void remover(UUID id) {
        getPorId(id); // valida existência antes de remover
        gerenciador.removerEntidade(id);
    }
}

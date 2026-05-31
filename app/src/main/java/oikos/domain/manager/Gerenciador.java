package oikos.domain.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import oikos.domain.model.Entidade;
import oikos.domain.model.Grupo;

/**
 * Classe genérica concreta para gerenciamento de coleções de entidades do domínio.
 * Mantém uma referência ao Grupo de origem para validações de contexto.
 *
 * @param <TipoEntidade> O tipo de entidade que este gerenciador controla (deve estender Entidade).
 */
public class Gerenciador<TipoEntidade extends Entidade> {
    
    @JsonProperty("entidades")
    private List<TipoEntidade> entidades;

    @JsonIgnore
    private Grupo grupoOrigem;

    /**
     * Construtor padrão (vazio) essencial para o Jackson conseguir instanciar
     * a classe durante a desserialização antes de popular as variáveis.
     */
    public Gerenciador() {
        this.entidades = new ArrayList<>();
    }

    /**
     * Cria um novo gerenciador vinculado a um grupo específico.
     * Utilizado para instanciação manual no seu código, não pelo Jackson.
     * * @param grupoOrigem Grupo ao qual esta coleção pertence.
     */
    public Gerenciador(Grupo grupoOrigem) {
        this.grupoOrigem = grupoOrigem;
        this.entidades = new ArrayList<>();
    }

    /**
     * Retorna a lista completa de entidades gerenciadas.
     * @return Lista contendo todas as entidades.
     */
    @JsonProperty("entidades")
    public List<TipoEntidade> getListaEntidades() {
        return entidades;
    }

    /**
     * Permite ao Jackson injetar a lista desserializada.
     */
    @JsonProperty("entidades")
    public void setListaEntidades(List<TipoEntidade> entidades) {
        this.entidades = entidades;
    }

    /**
     * Restaura a referência ao grupo de origem após a desserialização.
     * @param grupoOrigem O grupo dono deste gerenciador.
     */
    @JsonIgnore
    public void setGrupoOrigem(Grupo grupoOrigem) {
        this.grupoOrigem = grupoOrigem;
    }

    /**
     * Busca uma entidade específica dentro da coleção pelo seu UUID.
     * @param id O UUID da entidade a ser buscada.
     * @return A entidade encontrada, ou null se não existir.
     */
    public TipoEntidade getPorId(UUID id) {
        for (TipoEntidade entidade : entidades) {
            if (entidade.getId().equals(id)) {
                return entidade;
            }
        }
        return null;
    }

    /**
     * Adiciona uma entidade à coleção.
     * @param entidade A entidade a ser inserida.
     */
    public void adicionarEntidade(TipoEntidade entidade) {
        entidades.add(entidade);
    }

    /**
     * Remove uma entidade da coleção com base em seu UUID.
     * @param id O UUID da entidade a ser removida.
     */
    public void removerEntidade(UUID id) {
        entidades.removeIf(entidade -> entidade.getId().equals(id));
    }
}
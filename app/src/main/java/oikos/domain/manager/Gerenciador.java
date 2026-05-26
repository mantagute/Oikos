package oikos.domain.manager;

import oikos.domain.model.Entidade;
import oikos.domain.model.Grupo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Gerenciador <TipoEntidade extends Entidade>{
    private List<TipoEntidade> entidades;
    private Grupo grupoOrigem;

    public Gerenciador(Grupo grupoOrigem) {
        this.grupoOrigem = grupoOrigem;
        this.entidades = new ArrayList<>();
    }

    public List<TipoEntidade> getListaEntidades() {
        return entidades;
    }

    public TipoEntidade getPorId(UUID id) {
        for (TipoEntidade entidade : entidades) {
            if (entidade.getId().equals(id)) {
                return entidade;
            }
        }
        return null;
    }

    public void adicionarEntidade(TipoEntidade entidade) {
        entidades.add(entidade);
    }

    public void removerEntidade(UUID id) {
        entidades.removeIf(entidade -> entidade.getId().equals(id));
    }
}

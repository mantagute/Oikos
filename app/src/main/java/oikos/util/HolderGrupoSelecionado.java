package oikos.util;

import java.util.UUID;

/**
 * Classe utilitária que mantém, em memória, o UUID do "grupo ativo".
 */
public class HolderGrupoSelecionado {

    /** UUID do grupo atualmente selecionado, ou {@code null} se nenhum estiver ativo. */
    private UUID grupoSelecionadoId;

    /**
     * Define o identificador do grupo que está ativo.
     *
     * @param id UUID do grupo – não pode ser {@code null}
     */
    public void setGrupoSelecionadoId(UUID id) {
        this.grupoSelecionadoId = id;
    }

    /**
     * Obtém o UUID do grupo ativo.
     *
     */
    public UUID getGrupoSelecionadoId() {
        return grupoSelecionadoId;
    }

    /**
     * Limpa a seleção, removendo o ID armazenado.
     */
    public void clear() {
        this.grupoSelecionadoId = null;
    }
}

package oikos.domain.manager;

import oikos.domain.model.Grupo;
import oikos.domain.model.Evento;

/**
 * Gerenciador específico para a coleção de Eventos de um Grupo.
 */
public class GerenciadorEventos extends Gerenciador<Evento> {
    
    public GerenciadorEventos(Grupo grupoOrigem) {
        super(grupoOrigem);
    }
}

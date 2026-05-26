package oikos.domain.manager;

import oikos.domain.model.Grupo;
import oikos.domain.model.Evento;

public class GerenciadorEventos extends Gerenciador<Evento> {
    
    public GerenciadorEventos(Grupo grupoOrigem) {
        super(grupoOrigem);
    }
}

package oikos.api.evento;

import java.util.UUID;

import oikos.domain.model.Evento;

public record EventoResponse(UUID id, String nome, int pontos) {
    public static EventoResponse from(Evento evento) {
        return new EventoResponse(evento.getId(), evento.getNome(), evento.getPontos());
    }
}

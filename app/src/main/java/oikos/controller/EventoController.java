package oikos.controller;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oikos.api.evento.CriarEventoRequest;
import oikos.api.evento.EventoResponse;
import oikos.service.ServicoEventos;
import oikos.domain.model.Evento;

@RestController
@RequestMapping("/grupos/{grupoId}/eventos")

public class EventoController {
    
    private final ServicoEventos servicoEventos;

    public EventoController(ServicoEventos servicoEventos) {
        this.servicoEventos = servicoEventos;
    }

    @GetMapping
    public List<EventoResponse> getListaEventos(@PathVariable UUID grupoId) {
        List<Evento> eventos = servicoEventos.getLista(grupoId);
        List<EventoResponse> responses = new ArrayList<>();

        for (Evento evento : eventos) {
            responses.add(EventoResponse.from(evento));
        }

        return responses;
    }

    @GetMapping("/{eventoId}")
    public EventoResponse getEventoPorId(@PathVariable UUID grupoId, @PathVariable UUID eventoId) {
        return EventoResponse.from(servicoEventos.getPorId(grupoId, eventoId));
    }

    @PostMapping
    public ResponseEntity<EventoResponse> criarEvento(@PathVariable UUID grupoId, @RequestBody CriarEventoRequest request) {
        Evento novoEvento = new Evento(request.nome(), request.pontos());
        servicoEventos.adicionar(grupoId, novoEvento);
        EventoResponse response = EventoResponse.from(novoEvento);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{eventoId}")
    public ResponseEntity<Void> excluirEvento(@PathVariable UUID grupoId, @PathVariable UUID eventoId) {
        servicoEventos.remover(grupoId,eventoId);
        return ResponseEntity.noContent().build();
    }
}

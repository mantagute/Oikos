package oikos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oikos.api.notificacao.NotificacaoResponse;
import oikos.domain.model.Notificacao;
import oikos.service.ServicoNotificacoes;
import oikos.api.notificacao.CriarNotificacaoRequest;


@RestController
@RequestMapping("/grupos/{grupoId}/notificacoes")

public class NotificacaoController {

    private final ServicoNotificacoes servicoNotificacoes;

    public NotificacaoController(ServicoNotificacoes servicoNotificacoes) {
        this.servicoNotificacoes = servicoNotificacoes;
    }
    
    @GetMapping
    public List<NotificacaoResponse> getListaNotificacoes(@PathVariable UUID grupoId) {
        List<Notificacao> notificacoes = servicoNotificacoes.getLista(grupoId);
        List<NotificacaoResponse> response = new ArrayList<>();

        for (Notificacao notificacao: notificacoes) {
            response.add(NotificacaoResponse.from(notificacao));
        }

        return response;
    }

    @GetMapping("/{notificacaoId}") 
    public NotificacaoResponse getNotificacaoPorId(@PathVariable UUID grupoId, @PathVariable UUID notificacaoId) {
        return NotificacaoResponse.from(servicoNotificacoes.getPorId(grupoId, notificacaoId));
    }

    @PostMapping 
    public ResponseEntity<NotificacaoResponse> criarNotificacao(@PathVariable UUID grupoId, @RequestBody CriarNotificacaoRequest request) {
        Notificacao novaNotificacao = new Notificacao(request.mensagem(), request.idParoquia());
        servicoNotificacoes.adicionar(grupoId, novaNotificacao);
        
        NotificacaoResponse response = NotificacaoResponse.from(novaNotificacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{notificacaoId}/lida")
    public ResponseEntity<Void> marcarComoLida(@PathVariable UUID grupoId, @PathVariable UUID notificacaoId) {
        servicoNotificacoes.marcarComoLida(grupoId, notificacaoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{notificacaoId}")
    public ResponseEntity<Void> excluirNotificacao(@PathVariable UUID grupoId, @PathVariable UUID notificacaoId) {
        servicoNotificacoes.remover(grupoId, notificacaoId);

        return ResponseEntity.noContent().build();
    }

}

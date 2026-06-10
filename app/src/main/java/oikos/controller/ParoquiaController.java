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

import oikos.api.paroquia.CriarParoquiaRequest;
import oikos.api.autenticar.AutenticarRequest;
import oikos.api.paroquia.ParoquiaResponse;
import oikos.api.paroquia.VincularGrupoRequest;
import oikos.api.paroquia.EnviarNotificacaoRequest;
import oikos.api.grupo.GrupoResponse;
import oikos.domain.model.Grupo;
import oikos.domain.model.Paroquia;
import oikos.service.ServicoParoquias;

@RestController
@RequestMapping("paroquias")

public class ParoquiaController {
    
    private final ServicoParoquias servicoParoquias;

    public ParoquiaController(ServicoParoquias servicoParoquias) {
        this.servicoParoquias = servicoParoquias;
    }

    @GetMapping("/{id}")
    public ParoquiaResponse getParoquiaPorId(@PathVariable UUID id) {
        Paroquia paroquia = servicoParoquias.getPorId(id);
        return ParoquiaResponse.from(paroquia);
    }

    @GetMapping
    public List<ParoquiaResponse> getListaParoquias() {
        List<Paroquia> paroquias = servicoParoquias.getLista();
        List<ParoquiaResponse> responses = new ArrayList<>();
        for (Paroquia paroquia : paroquias) {
            responses.add(ParoquiaResponse.from(paroquia));
        }
        return responses;
    }

    @PostMapping
    public ResponseEntity<ParoquiaResponse> criarGrupo(@RequestBody CriarParoquiaRequest request) {
        String nome = request.nome();
        String senha = request.senha();

        Paroquia paroquiaCriada = servicoParoquias.criar(nome, senha);
        ParoquiaResponse response = ParoquiaResponse.from(paroquiaCriada);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/autenticar") 
    public boolean autenticar(@PathVariable UUID id, @RequestBody AutenticarRequest request) {
        Paroquia paroquia = servicoParoquias.getPorId(id);
        return paroquia.autenticarSenha(request.senha());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirParoquia(@PathVariable UUID id, @RequestBody AutenticarRequest request) {
        String senha = request.senha();
        servicoParoquias.excluir(id, senha);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/grupos")
    public List<GrupoResponse> getGruposVinculados(@PathVariable UUID id)  {
        Paroquia paroquia = servicoParoquias.getPorId(id);
        List<Grupo> gruposVinculados = paroquia.getGerenciadorGrupos().getListaEntidades();
        List<GrupoResponse> responses = new ArrayList<>();
        
        for (Grupo grupo : gruposVinculados) {
            responses.add(GrupoResponse.from(grupo));
        }

        return responses;
    }

    @PostMapping("{id}/vinculos") 
    public ResponseEntity<Void> vincularGrupo(@PathVariable UUID id, @RequestBody VincularGrupoRequest request) {
        UUID idGrupo = request.idGrupo();
        servicoParoquias.vincularGrupo(id, idGrupo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/vinculos/{grupoId}")
    public ResponseEntity<Void> desvincularGrupo(@PathVariable UUID id, @PathVariable UUID grupoId) {
        servicoParoquias.desvincularGrupo(id, grupoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/notificacoes")
    public ResponseEntity<Void> enviarNotificacoes(@PathVariable UUID id, @RequestBody EnviarNotificacaoRequest request) {
        servicoParoquias.enviarNotificacoes(id, request.mensagem(), request.gruposIds());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/solicitar-vinculo")
    public ResponseEntity<Void> solicitarVinculo(@PathVariable UUID id, @RequestBody VincularGrupoRequest request) {
        servicoParoquias.solicitarVinculo(id, request.idGrupo());
        return ResponseEntity.ok().build();
    }

}


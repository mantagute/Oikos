package oikos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import oikos.api.grupo.CriarGrupoRequest;
import oikos.api.grupo.ExcluirGrupoRequest;
import oikos.api.grupo.GrupoResponse;
import oikos.api.grupo.PontuarRequest;
import oikos.api.grupo.RedefinirMetaRequest;
import oikos.domain.model.Grupo;
import oikos.service.ServicoGrupos;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final ServicoGrupos servicoGrupos;

    public GrupoController(ServicoGrupos servicoGrupos) {
        this.servicoGrupos = servicoGrupos;
    }

    @GetMapping
    public List<GrupoResponse> getListaGrupos() {
        List<Grupo> grupos = servicoGrupos.getListaGrupos();
        List<GrupoResponse> responses = new ArrayList<>();

        for (Grupo grupo : grupos) {
            GrupoResponse response = GrupoResponse.from(grupo);
            responses.add(response);
        }

        return responses;
    }

    @PostMapping
    public ResponseEntity<GrupoResponse> criarGrupo(@RequestBody CriarGrupoRequest request) {
        String nome = request.nome();
        String senha = request.senha();

        Grupo grupoCriado = servicoGrupos.criarGrupo(nome, senha);
        GrupoResponse response = GrupoResponse.from(grupoCriado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public GrupoResponse getGrupoPorId(@PathVariable UUID id) {
        Grupo grupoEncontrado = servicoGrupos.getGrupoPorId(id);
        GrupoResponse response = GrupoResponse.from(grupoEncontrado);

        return response;
    }

    @GetMapping("/{id}/{senhaIn}")
    public boolean autenticarSenhaGrupo(@PathVariable UUID id, @PathVariable String senhaIn) {
        Grupo grupoEncontrado = servicoGrupos.getGrupoPorId(id);
        return grupoEncontrado.autenticarSenha(senhaIn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirGrupo(@PathVariable UUID id, @RequestBody ExcluirGrupoRequest request) {
        String senha = request.senha();

        servicoGrupos.excluirGrupo(id, senha);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/meta")
    public GrupoResponse redefinirMeta(@PathVariable UUID id, @RequestBody RedefinirMetaRequest request) {
        int novaMeta = request.meta();

        Grupo grupoAtualizado = servicoGrupos.redefinirMeta(id, novaMeta);
        GrupoResponse response = GrupoResponse.from(grupoAtualizado);

        return response;
    }

    @PostMapping("/{id}/pontuar")
    public ResponseEntity<Void> pontuar(@PathVariable UUID id, @RequestBody PontuarRequest request) {
        UUID pessoaId = request.pessoaId();
        UUID eventoId = request.eventoId();

        servicoGrupos.pontuar(id, pessoaId, eventoId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/classificar")
    public String getClassificacaoGrupo(@PathVariable UUID id) {
        return servicoGrupos.getGrupoPorId(id).getClassificacao();
    }
}

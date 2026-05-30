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

import oikos.api.pessoa.CriarPessoaRequest;
import oikos.api.pessoa.PessoaResponse;
import oikos.service.ServicoPessoas;
import oikos.domain.model.Pessoa;

@RestController
@RequestMapping("/grupos/{grupoId}/pessoas")
public class PessoaController {
    
    private final ServicoPessoas servicoPessoas;

    public PessoaController(ServicoPessoas servicoPessoas) {
        this.servicoPessoas = servicoPessoas;
    }

    @GetMapping
    public List<PessoaResponse> getListaPessoas(@PathVariable UUID grupoId) {
        List<Pessoa> pessoas = servicoPessoas.getLista(grupoId);
        List<PessoaResponse> responses = new ArrayList<>();

        for (Pessoa pessoa : pessoas) {
            responses.add(PessoaResponse.from(pessoa));
        }

        return responses;
    }

    @GetMapping("/{pessoaId}")
    public PessoaResponse getPessoaPorId(@PathVariable UUID grupoId, @PathVariable UUID pessoaId) {
        return PessoaResponse.from(servicoPessoas.getPorId(grupoId, pessoaId));
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> criarPessoa(@PathVariable UUID grupoId, @RequestBody CriarPessoaRequest request) {
        Pessoa novaPessoa = new Pessoa(request.nome());
        servicoPessoas.adicionar(grupoId, novaPessoa);
        PessoaResponse response = PessoaResponse.from(novaPessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{pessoaId}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable UUID grupoId, @PathVariable UUID pessoaId) {
        servicoPessoas.remover(grupoId, pessoaId);
        return ResponseEntity.noContent().build();
    }

}

package oikos.api.pessoa;

import java.util.UUID;

import oikos.domain.model.Pessoa;

public record PessoaResponse(UUID id, String nome) {

    public static PessoaResponse from(Pessoa pessoa) {
        return new PessoaResponse(pessoa.getId(), pessoa.getNome());
    }
}

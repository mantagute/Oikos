package oikos.api.grupo;

import java.util.UUID;

import oikos.domain.model.Grupo;

public record GrupoResponse(
        UUID id,
        String nome,
        int meta,
        int pontuacaoAtual,
        int metasBatidas,
        String classificacao,
        int totalPessoas,
        int totalEventos) {

    public static GrupoResponse from(Grupo grupo) {
        return new GrupoResponse(
                grupo.getId(),
                grupo.getNome(),
                grupo.getMeta(),
                grupo.getPontuacaoAtual(),
                grupo.getMetasBatidas(),
                grupo.getClassificacao(),
                grupo.getGerenciadorPessoas().getListaEntidades().size(),
                grupo.getGerenciadorEventos().getListaEntidades().size());
    }
}

package oikos.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Notificacao;

@Service
public class ServicoNotificacoes extends ServicoEntidades<Notificacao>{

    private final ServicoParoquias servicoParoquias;

    public ServicoNotificacoes(ServicoGrupos servicoGrupos, ServicoParoquias servicoParoquias) {
        super(servicoGrupos);
        this.servicoParoquias = servicoParoquias;
    }

    protected Gerenciador<Notificacao> getGerenciadorPorGrupoId(UUID grupoId) {
        return servicoGrupos.getPorId(grupoId).getGerenciadorNotificacoes();
    }

    public void adicionar(UUID grupoId, Notificacao notificacao) {
        if (notificacao.getMensagem() == null || notificacao.getMensagem().isBlank()) {
            throw new IllegalArgumentException("Mensagem da notificação não pode ser vazia.");
        }

        Gerenciador<Notificacao> gerenciador = getGerenciadorPorGrupoId(grupoId);
        gerenciador.adicionarEntidade(notificacao);

        servicoGrupos.salvar();
    }

    public void marcarComoLida(UUID grupoId, UUID notificacaoId) {
        Notificacao notificacao = getPorId(grupoId, notificacaoId);
        notificacao.marcarComoLida();
        servicoGrupos.salvar();
    }

    public void aceitarVinculo(UUID grupoId, UUID notificacaoId) {
        Notificacao notificacao = getPorId(grupoId, notificacaoId);
        if (!"VINCULO".equals(notificacao.getTipo())) {
            throw new IllegalArgumentException("Esta notificação não é uma solicitação de vínculo.");
        }
        servicoParoquias.vincularGrupo(notificacao.getIdParoquia(), grupoId);
        getGerenciadorPorGrupoId(grupoId).removerEntidade(notificacaoId);
        servicoGrupos.salvar();
    }
}

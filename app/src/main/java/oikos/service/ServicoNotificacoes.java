package oikos.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Notificacao;

/**
 * Serviço de notificações que especializa {@link ServicoEntidades} implementando
 * validações específicas ao gerenciar notificações em um grupo.
 * Também coordena a aceitação de solicitações de vínculo entre paróquia e grupo.
 */
@Service
public class ServicoNotificacoes extends ServicoEntidades<Notificacao>{

    private final ServicoParoquias servicoParoquias;

    /**
     * Cria o serviço vinculado aos serviços de grupos e paróquias.
     *
     * @param servicoGrupos    Serviço de grupos, usado para localizar o grupo.
     * @param servicoParoquias Serviço de paróquias, usado para aceitar vínculos.
     */
    public ServicoNotificacoes(ServicoGrupos servicoGrupos, ServicoParoquias servicoParoquias) {
        super(servicoGrupos);
        this.servicoParoquias = servicoParoquias;
    }

    /**
     * Retorna o gerenciador de notificações do grupo informado.
     *
     * @param grupoId UUID do grupo.
     * @return Gerenciador de notificações do grupo.
     */
    @Override
    protected Gerenciador<Notificacao> getGerenciadorPorGrupoId(UUID grupoId) {
        return servicoGrupos.getPorId(grupoId).getGerenciadorNotificacoes();
    }

    /**
     * Adiciona uma nova notificação ao grupo informado, validando a mensagem.
     *
     * @param grupoId     UUID do grupo.
     * @param notificacao A notificação a ser adicionada.
     * @throws IllegalArgumentException se a mensagem for vazia ou nula.
     */
    @Override
    public void adicionar(UUID grupoId, Notificacao notificacao) {
        if (notificacao.getMensagem() == null || notificacao.getMensagem().isBlank()) {
            throw new IllegalArgumentException("Mensagem da notificação não pode ser vazia.");
        }

        Gerenciador<Notificacao> gerenciador = getGerenciadorPorGrupoId(grupoId);
        gerenciador.adicionarEntidade(notificacao);

        servicoGrupos.salvar();
    }

    /**
     * Marca uma notificação como lida.
     *
     * @param grupoId        UUID do grupo.
     * @param notificacaoId  UUID da notificação a ser marcada.
     */
    public void marcarComoLida(UUID grupoId, UUID notificacaoId) {
        Notificacao notificacao = getPorId(grupoId, notificacaoId);
        notificacao.marcarComoLida();
        servicoGrupos.salvar();
    }

    /**
     * Aceita uma solicitação de vínculo entre paróquia e grupo.
     * Vincula o grupo à paróquia e remove a notificação de solicitação.
     *
     * @param grupoId        UUID do grupo.
     * @param notificacaoId  UUID da notificação de solicitação.
     * @throws IllegalArgumentException se a notificação não for do tipo "VINCULO".
     */
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

package oikos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import oikos.persistence.PersistenciaJson;

import oikos.domain.model.Paroquia;
import oikos.domain.model.Grupo;
import oikos.domain.model.Notificacao;


/**
 * Serviço que gerencia as paróquias no sistema, estendendo {@link ServicoEscopoMaior}.
 * Responsável por operações específicas de paróquias: vinculação de grupos,
 * desvinculação, solicitação de vínculo e envio de notificações em massa.
 */
@Service
public class ServicoParoquias extends ServicoEscopoMaior<Paroquia> {
    private final ServicoGrupos servicoGrupos;

    /**
     * Cria a instância de persistência JSON configurada para paróquias.
     *
     * @return {@link PersistenciaJson} configurada para {@code List<Paroquia>}.
     */
    private static PersistenciaJson<List<Paroquia>> criarPersistencia() {
        return new PersistenciaJson<List<Paroquia>>("../data/paroquias.json",new TypeReference<List<Paroquia>>() {}, () -> new ArrayList<>());
    }

    /**
     * Construtor padrão. Inicializa a persistência e vincula o serviço de grupos.
     *
     * @param servicoGrupos Serviço de grupos para operações que envolvem grupos.
     */
    public ServicoParoquias(ServicoGrupos servicoGrupos) {
        super(criarPersistencia());
        this.servicoGrupos = servicoGrupos;
    }

    /**
     * Instancia uma nova {@link Paroquia} com nome e senha.
     *
     * @param nome  Nome da paróquia.
     * @param senha Senha da paróquia.
     * @return Nova instância de {@link Paroquia}.
     */
    @Override
    protected Paroquia instanciar(String nome, String senha) {
        return new Paroquia(nome, senha);
    }

    /**
     * Retorna o nome de uma paróquia. Usado pela superclasse para validação de duplicatas.
     *
     * @param paroquia A paróquia cujo nome se deseja obter.
     * @return O nome da paróquia.
     */
    @Override
    protected String getNome(Paroquia paroquia) {
        return paroquia.getNome();
    }

    /**
     * Vincula um grupo a uma paróquia.
     *
     * @param idParoquia UUID da paróquia.
     * @param idGrupo    UUID do grupo a ser vinculado.
     * @throws IllegalArgumentException se o grupo já estiver vinculado à paróquia.
     */
    public void vincularGrupo(UUID idParoquia, UUID idGrupo) {
        Paroquia paroquia = getPorId(idParoquia);
        Grupo grupo = servicoGrupos.getPorId(idGrupo);
        if (paroquia.getGerenciadorGrupos().getPorId(idGrupo) != null) {
            throw new IllegalArgumentException("Grupo já vinculado a esta paróquia");
        }
        paroquia.getGerenciadorGrupos().adicionarEntidade(grupo);
        salvar();
    }

    /**
     * Remove o vínculo entre um grupo e uma paróquia.
     *
     * @param idParoquia UUID da paróquia.
     * @param idGrupo    UUID do grupo a ser desvinculado.
     */
    public void desvincularGrupo(UUID idParoquia, UUID idGrupo) {
        Paroquia paroquia = getPorId(idParoquia);
        paroquia.getGerenciadorGrupos().removerEntidade(idGrupo);
        salvar();
    }

    /**
     * Envia uma solicitação de vínculo de uma paróquia para um grupo.
     * Cria uma notificação do tipo "VINCULO" no grupo destinatário.
     *
     * @param idParoquia UUID da paróquia solicitante.
     * @param idGrupo    UUID do grupo destinatário.
     * @throws IllegalArgumentException se o grupo já estiver vinculado ou já houver solicitação pendente.
     */
    public void solicitarVinculo(UUID idParoquia, UUID idGrupo) {
        Paroquia paroquia = getPorId(idParoquia);
        Grupo grupo = servicoGrupos.getPorId(idGrupo);

        if (paroquia.getGerenciadorGrupos().getPorId(idGrupo) != null) {
            throw new IllegalArgumentException("Grupo já vinculado a esta paróquia");
        }

        boolean jaSolicitado = grupo.getGerenciadorNotificacoes().getListaEntidades().stream()
            .anyMatch(notificacao -> "VINCULO".equals(notificacao.getTipo()) && idParoquia.equals(notificacao.getIdParoquia()) && !notificacao.isLida());
        if (jaSolicitado) {
            throw new IllegalArgumentException("Já existe uma solicitação de vínculo pendente para este grupo.");
        }

        Notificacao notificacao = new Notificacao(
            "A paróquia \"" + paroquia.getNome() + "\" está solicitando vínculo. Aceite para compartilhar seus dados.",
            idParoquia,
            "VINCULO"
        );
        grupo.getGerenciadorNotificacoes().adicionarEntidade(notificacao);
        servicoGrupos.salvar();
    }

    /**
     * Envia uma notificação para grupos vinculados à paróquia.
     * Se {@code gruposIds} for nulo, envia para todos os grupos vinculados.
     *
     * @param idParoquia UUID da paróquia remetente.
     * @param mensagem   Conteúdo da notificação.
     * @param gruposIds  UUIDs dos grupos destinatários, ou {@code null} para todos.
     * @throws IllegalArgumentException se a mensagem for vazia ou um grupo não estiver vinculado.
     */
    public void enviarNotificacoes(UUID idParoquia, String mensagem, List<UUID> gruposIds) {
        if (mensagem == null || mensagem.isBlank()) {
            throw new IllegalArgumentException("Mensagem da notificação não pode ser vazia.");
        }

        Paroquia paroquia = getPorId(idParoquia);

        List<UUID> destinatarios = (gruposIds != null) ? gruposIds  : paroquia.getGerenciadorGrupos().getListaEntidades().stream().map(Grupo::getId).toList();

        for (UUID grupoId : destinatarios) {
            Grupo grupo = paroquia.getGerenciadorGrupos().getPorId(grupoId);
            if (grupo == null) {
                throw new IllegalArgumentException("O grupo " + grupoId + " não está vinculado a esta paróquia.");
            }
            grupo.getGerenciadorNotificacoes().adicionarEntidade(new Notificacao(mensagem, idParoquia));
        }

        servicoGrupos.salvar();
    }

}



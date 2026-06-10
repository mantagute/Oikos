package oikos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import oikos.domain.model.Evento;
import oikos.domain.model.Grupo;
import oikos.domain.model.Pessoa;
import oikos.persistence.PersistenciaJson;

/**
 * Serviço que gerencia os grupos em memória, estendendo {@link ServicoEscopoMaior}.
 * Responsável pelas operações específicas de grupos: pontuação e redefinição de meta.
 * Operações CRUD e persistência são herdadas da superclasse.
 */
@Service
public class ServicoGrupos extends ServicoEscopoMaior<Grupo> {

    /**
     * Cria a instância de persistência JSON configurada para grupos.
     *
     * @return {@link PersistenciaJson} configurada para {@code List<Grupo>}.
     */
    private static PersistenciaJson<List<Grupo>> criarPersistencia() {
        return new PersistenciaJson<List<Grupo>>("../data/grupos.json", new TypeReference<List<Grupo>>() {}, () -> new ArrayList<>());
    }

    /**
     * Construtor padrão. Inicializa a persistência via factory method estática
     * para evitar chamada de métodos abstratos no construtor da superclasse.
     */
    public ServicoGrupos() {
        super(criarPersistencia());
    }

    /**
     * Instancia um novo {@link Grupo} com nome e senha.
     *
     * @param nome  Nome do grupo.
     * @param senha Senha do grupo.
     * @return Nova instância de {@link Grupo}.
     */
    @Override
    protected Grupo instanciar(String nome, String senha) {
        return new Grupo(nome, senha);
    }

    /**
     * Retorna o nome de um grupo. Usado pela superclasse para validação de duplicatas.
     *
     * @param grupo O grupo cujo nome se deseja obter.
     * @return O nome do grupo.
     */
    @Override
    protected String getNome(Grupo grupo) {
        return grupo.getNome();
    }

    /**
     * Restaura as referências transientes dos gerenciadores após a desserialização do JSON.
     * O {@code grupoOrigem} é marcado com {@code @JsonIgnore} e precisa ser reestabelecido
     * manualmente após a recuperação.
     */
    @Override
    protected void posRecuperar() {
        for (Grupo grupo : entidades) {
            if (grupo.getGerenciadorEventos() != null) {
                grupo.getGerenciadorEventos().setGrupoOrigem(grupo);
            }
            if (grupo.getGerenciadorPessoas() != null) {
                grupo.getGerenciadorPessoas().setGrupoOrigem(grupo);
            }
            if (grupo.getGerenciadorNotificacoes() != null) {
                grupo.getGerenciadorNotificacoes().setGrupoOrigem(grupo);
            }
        }
    }

    /**
     * Registra a realização de uma atividade por uma pessoa em um evento,
     * pontuando o grupo especificado.
     *
     * @param grupoId  Identificador do grupo.
     * @param pessoaId Identificador da pessoa que realizou a atividade.
     * @param eventoId Identificador do evento realizado.
     * @throws IllegalArgumentException se a pessoa ou evento não pertencerem ao grupo.
     * @throws NoSuchElementException   se o grupo não for encontrado.
     */
    public void pontuar(UUID grupoId, UUID pessoaId, UUID eventoId) {
        Grupo grupo = getPorId(grupoId);
        Pessoa pessoa = grupo.getGerenciadorPessoas().getPorId(pessoaId);
        if (pessoa == null) {
            throw new IllegalArgumentException("Pessoa não encontrada no grupo");
        }

        Evento evento = grupo.getGerenciadorEventos().getPorId(eventoId);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado no grupo");
        }

        grupo.pontuar(pessoa, evento);
        salvar();
    }

    /**
     * Redefine a meta de pontos de um grupo específico.
     *
     * @param id       Identificador do grupo.
     * @param novaMeta Novo valor da meta. Deve ser maior que zero.
     * @return O {@link Grupo} atualizado.
     * @throws IllegalArgumentException se a meta for menor ou igual a zero.
     * @throws NoSuchElementException   se o grupo não for encontrado.
     */
    public Grupo redefinirMeta(UUID id, int novaMeta) {
        if (novaMeta <= 0) {
            throw new IllegalArgumentException("Meta deve ser maior que zero");
        }

        Grupo grupo = getPorId(id);
        grupo.redefinirMeta(novaMeta);
        salvar();

        return grupo;
    }

    /**
     * Redefine a senha de um grupo, exigindo a senha atual para autorização.
     *
     * @param id         Identificador do grupo.
     * @param senhaAtual Senha atual do grupo para validação.
     * @param novaSenha  Nova senha desejada.
     * @throws IllegalArgumentException se a senha atual estiver incorreta.
     * @throws NoSuchElementException   se o grupo não for encontrado.
     */
    public void redefinirSenha(UUID id, String senhaAtual, String novaSenha) {
        Grupo grupo = getPorId(id);
        grupo.redefinirSenha(senhaAtual, novaSenha);
        salvar();
    }
}

package oikos.service;

import oikos.domain.model.Grupo;
import oikos.util.HolderGrupoSelecionado;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Serviço que gerencia os grupos em memória.
 * <p>
 * Responsável por criar, listar, buscar e excluir grupos,
 * além de controlar qual grupo está ativo via {@link HolderGrupoSelecionado}.
 * </p>
 */
public class ServicoGrupos {

    private final List<Grupo> grupos = new ArrayList<>();
    private final HolderGrupoSelecionado holder;

    public ServicoGrupos(HolderGrupoSelecionado holder) {
        this.holder = holder;
    }

    /**
     * Cria um novo grupo e o adiciona à lista em memória.
     * Valida que nome e senha não sejam vazios e que o nome não esteja duplicado.
     * O grupo recém-criado é automaticamente marcado como ativo.
     *
     * @param nome  nome do grupo (não pode ser vazio, deve ser único)
     * @param senha senha do grupo (não pode ser vazia)
     * @return o {@link Grupo} criado
     * @throws IllegalArgumentException se nome/senha forem inválidos ou nome já existir
     */
    public Grupo criarGrupo(String nome, String senha) {

        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (senha.isBlank()){
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        if (grupos.stream().anyMatch(grupo -> grupo.getNome().equalsIgnoreCase(nome))) {    
            throw new IllegalArgumentException("Já existe um grupo com esse nome");
        }

        Grupo novo = new Grupo(nome, senha);
        grupos.add(novo);
        holder.setGrupoSelecionadoId(novo.getId());
        return novo;
    }

    /** Retorna uma cópia da lista de todos os grupos cadastrados. */
    public List<Grupo> getListaGrupos() {
        return new ArrayList<>(grupos);
    }

    /**
     * Busca um grupo pelo seu UUID.
     *
     * @param id identificador do grupo
     * @return o {@link Grupo} encontrado
     * @throws NoSuchElementException se nenhum grupo tiver o ID informado
     */
    public Grupo getGrupoPorId(UUID id) {
        for (Grupo grupo : grupos) {
            if ((grupo.getId().equals(id))) {
                return grupo;
            }
        }
        throw new NoSuchElementException("Grupo não encontrado");
    }

    /**
     * Exclui um grupo após validar a senha informada.
     * Se o grupo excluído era o ativo, a seleção é limpa.
     *
     * @param id    identificador do grupo a excluir
     * @param senha senha para autorizar a exclusão
     * @throws NoSuchElementException se o grupo não for encontrado
     * @throws SecurityException      se a senha estiver incorreta
     */
    public void excluirGrupo(UUID id, String senha) {
        Grupo grupo = getGrupoPorId(id);
        if (!grupo.autenticarSenha(senha)) {
            throw new SecurityException("Senha inválida para exclusão do grupo");
        }

        grupos.remove(grupo);

        // limpa seleção se o grupo excluído era o ativo
        UUID selecionado = holder.getGrupoSelecionadoId();
        if (selecionado != null && selecionado.equals(id)) {
            holder.clear();
        }
    }


    /**
     * Define qual grupo está ativo.
     * Valida a existência do grupo antes de marcar como selecionado.
     *
     * @param id identificador do grupo a selecionar
     * @throws NoSuchElementException se o grupo não for encontrado
     */
    public void selecionarGrupo(UUID id) {
        if (getGrupoPorId(id) == null) {
            throw new NoSuchElementException("Grupo não encontrado");
        }
        holder.setGrupoSelecionadoId(id);
    }

    /**
     * Retorna o grupo atualmente selecionado.
     *
     * @return o {@link Grupo} ativo
     * @throws NoSuchElementException se nenhum grupo estiver selecionado ou o ID não existir
     */
    public Grupo getGrupoSelecionado() {
        UUID id = holder.getGrupoSelecionadoId();
        return getGrupoPorId(id);
    }

}

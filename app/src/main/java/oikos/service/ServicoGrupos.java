package oikos.service;

import oikos.domain.model.Grupo;
import oikos.domain.model.Pessoa;
import oikos.domain.interfaces.Persistivel;
import oikos.domain.model.Evento;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.io.File;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

/**
 * Serviço que gerencia os grupos em memória.
 * <p>
 * Responsável por criar, listar, buscar e excluir grupos.
 * </p>
 */
@Service
public class ServicoGrupos implements Persistivel {

    private List<Grupo> grupos = new ArrayList<>();

    /**
     * Cria um novo grupo e o adiciona à lista em memória.
     * Valida que nome e senha não sejam vazios e que o nome não esteja duplicado.
     *
     * @param nome  nome do grupo (não pode ser vazio, deve ser único)
     * @param senha senha do grupo (não pode ser vazia)
     * @return o {@link Grupo} criado
     * @throws IllegalArgumentException se nome/senha forem inválidos ou nome já
     *                                  existir
     */
    public Grupo criarGrupo(String nome, String senha) {

        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (senha.isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        if (grupos.stream().anyMatch(grupo -> grupo.getNome().equalsIgnoreCase(nome))) {
            throw new IllegalArgumentException("Já existe um grupo com esse nome");
        }

        Grupo novo = new Grupo(nome, senha);
        grupos.add(novo);
        salvar();
        return novo;
    }

    /**
     * Retorna uma cópia da lista de todos os grupos cadastrados.
     *
     * @return lista de {@link Grupo} disponíveis no sistema
     */
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
            if (grupo.getId().equals(id)) {
                return grupo;
            }
        }
        throw new NoSuchElementException("Grupo não encontrado");
    }

    /**
     * Exclui um grupo após validar a senha informada.
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
        salvar();
    }

    /**
     * Registra a realização de uma atividade por uma pessoa em um evento,
     * pontuando o grupo especificado.
     *
     * @param grupoId  identificador do grupo
     * @param pessoaId identificador da pessoa que realizou a atividade
     * @param eventoId identificador do evento realizado
     * @throws IllegalArgumentException se a pessoa ou evento não pertencerem ao grupo
     * @throws NoSuchElementException   se o grupo não for encontrado
     */
    public void pontuar(UUID grupoId, UUID pessoaId, UUID eventoId) {
        Grupo grupo = getGrupoPorId(grupoId);
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
     * @param id       identificador do grupo
     * @param novaMeta novo valor da meta
     * @return o {@link Grupo} atualizado
     * @throws IllegalArgumentException se a meta for menor ou igual a zero
     * @throws NoSuchElementException   se o grupo não for encontrado
     */
    public Grupo redefinirMeta(UUID id, int novaMeta) {
        if (novaMeta <= 0) {
            throw new IllegalArgumentException("Meta deve ser maior que zero");
        }

        Grupo grupo = getGrupoPorId(id);
        grupo.redefinirMeta(novaMeta);
        salvar();

        return grupo;
    }

    @Override
    /**
     * Salva a lista de grupos em um arquivo json, com o nome grupos.json
     * 
     * @return String o nome do arquivo gerado, que por padrão é grupos.json
     */
    public String salvar() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("grupos.json"), this.grupos);
            return "grupos.json";
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar os grupos em um arquivo JSON", e);
        }
    }

    @Override
    @PostConstruct
    /**
     * Recupera a lista de grupos do arquivo grupos.json, se ele existir
     */
    public void recuperar() {
        File arquivo = new File("grupos.json");
        if (!arquivo.exists()) {
            this.grupos = new ArrayList<>();
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.grupos = mapper.readValue(arquivo, new TypeReference<List<Grupo>>() {
            });
            
            for (Grupo grupo : this.grupos) {
                grupo.getGerenciadorEventos().setGrupoOrigem(grupo);
                grupo.getGerenciadorPessoas().setGrupoOrigem(grupo);
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao recuperar os grupos do arquivo JSON", e);
        }
    }
}

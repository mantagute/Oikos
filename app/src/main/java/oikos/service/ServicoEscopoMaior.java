package oikos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import oikos.domain.interfaces.Autenticavel;
import oikos.domain.interfaces.Persistivel;
import oikos.domain.model.Entidade;

/**
 * Classe abstrata base para serviços que gerenciam entidades de escopo maior,
 * como Grupos e Paróquias. Encapsula a lógica comum de persistência em JSON,
 * operações CRUD e autenticação por senha.
 *
 * @param <TipoEntidade> O tipo da entidade gerenciada. Deve estender {@link Entidade}
 *                       e implementar {@link Autenticavel}.
 */
public abstract class ServicoEscopoMaior<TipoEntidade extends Entidade & Autenticavel> {

    protected List<TipoEntidade> entidades = new ArrayList<>();
    protected final Persistivel<List<TipoEntidade>> persistencia;

    /**
     * Construtor que recebe a persistência já configurada pela subclasse.
     *
     * @param persistencia Implementação de {@link Persistivel} configurada pela subclasse.
     */
    public ServicoEscopoMaior(Persistivel<List<TipoEntidade>> persistencia) {
        this.persistencia = persistencia;
    }

    /**
     * Valida e cria uma nova entidade, adicionando-a à lista em memória
     * e persistindo imediatamente.
     *
     * @param nome  Nome da entidade. Não pode ser vazio ou duplicado.
     * @param senha Senha de acesso. Não pode ser vazia.
     * @return A entidade criada.
     * @throws IllegalArgumentException se nome ou senha forem inválidos,
     *                                  ou se já existir uma entidade com o mesmo nome.
     */
    public TipoEntidade criar(String nome, String senha) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        if (entidades.stream().anyMatch(e -> getNome(e).equalsIgnoreCase(nome))) {
            throw new IllegalArgumentException("Já existe um registro com esse nome");
        }

        TipoEntidade novaEntidade = instanciar(nome, senha);
        entidades.add(novaEntidade);
        salvar();
        return novaEntidade;
    }

    /**
     * Instancia uma nova entidade do tipo {@code TipoEntidade} com nome e senha.
     * Cada subclasse define como criar sua própria entidade.
     *
     * @param nome  Nome da entidade.
     * @param senha Senha da entidade.
     * @return Nova instância de {@code TipoEntidade}.
     */
    protected abstract TipoEntidade instanciar(String nome, String senha);

    /**
     * Retorna o nome de uma entidade. Necessário para validação de duplicatas,
     * já que o tipo genérico não expõe {@code getNome()} diretamente.
     *
     * @param entidade A entidade cujo nome se deseja obter.
     * @return O nome da entidade como {@code String}.
     */
    protected abstract String getNome(TipoEntidade entidade);

    /**
     * Retorna uma cópia da lista de todas as entidades em memória.
     *
     * @return Lista de entidades do tipo {@code TipoEntidade}.
     */
    public List<TipoEntidade> getLista() {
        return new ArrayList<>(entidades);
    }

    /**
     * Busca uma entidade pelo seu UUID.
     *
     * @param id Identificador único da entidade.
     * @return A entidade encontrada.
     * @throws NoSuchElementException se nenhuma entidade com o ID informado existir.
     */
    public TipoEntidade getPorId(UUID id) {
        return entidades.stream().filter(entidade -> entidade.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Registro não encontrado com id: " + id));
    }

    /**
     * Exclui uma entidade após validar a senha informada.
     *
     * @param id    Identificador da entidade a excluir.
     * @param senha Senha para autorizar a exclusão.
     * @throws NoSuchElementException se a entidade não for encontrada.
     * @throws SecurityException      se a senha estiver incorreta.
     */
    public void excluir(UUID id, String senha) {
        TipoEntidade entidade = getPorId(id);
        if (!entidade.autenticarSenha(senha)) {
            throw new SecurityException("Senha inválida para exclusão");
        }
        entidades.remove(entidade);
        salvar();
    }

    /**
     * Persiste a lista atual de entidades no arquivo JSON.
     *
     * @return Nome do arquivo gerado.
     */
    public String salvar() {
        return persistencia.salvar(this.entidades);
    }

    /**
     * Recupera a lista de entidades do arquivo JSON ao inicializar o serviço.
     * Executado automaticamente pelo Spring após a construção do bean.
     */
    @PostConstruct
    public void recuperar() {
        this.entidades = persistencia.recuperar();
        posRecuperar();
    }

    /**
     * Hook executado após a recuperação dos dados do JSON.
     * Subclasses podem sobrescrever para restaurar referências transientes
     * (ex: {@code grupoOrigem} nos gerenciadores do Grupo).
     */
    protected void posRecuperar() {
        // implementação opcional nas subclasses
    }
}

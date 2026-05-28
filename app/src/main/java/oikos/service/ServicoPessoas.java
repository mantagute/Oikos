package oikos.service;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Pessoa;

/**
 * Serviço de pessoas que especializa ServicoEntidades implementando
 * a validação específica ao cadastrar uma nova Pessoa no grupo ativo.
 */
public class ServicoPessoas extends ServicoEntidades<Pessoa> {

    /**
     * Cria o serviço vinculado ao serviço de grupos.
     *
     * @param servicoGrupos O serviço de grupos, que mantém o grupo ativo.
     */
    public ServicoPessoas(ServicoGrupos servicoGrupos) {
        super(servicoGrupos);
    }

    @Override
    protected Gerenciador<Pessoa> getGerenciadorAtual() {
        return servicoGrupos.getGrupoSelecionado().getGerenciadorPessoas();
    }

    /**
     * Adiciona uma nova pessoa ao grupo ativo, validando que o nome não seja vazio.
     *
     * @param pessoa A pessoa a ser adicionada.
     * @throws IllegalArgumentException se o nome da pessoa for vazio ou nulo.
     */
    public void adicionar(Pessoa pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da pessoa não pode ser vazio.");
        }
        getGerenciadorAtual().adicionarEntidade(pessoa);
    }
}

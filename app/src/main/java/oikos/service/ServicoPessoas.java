package oikos.service;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Pessoa;

/**
 * Serviço de pessoas que especializa ServicoEntidades implementando
 * a validação específica ao cadastrar uma nova Pessoa.
 */
public class ServicoPessoas extends ServicoEntidades<Pessoa> {

    /**
     * Cria o serviço vinculado ao gerenciador de pessoas do grupo.
     *
     * @param gerenciador O gerenciador que mantém a coleção de pessoas.
     */
    public ServicoPessoas(Gerenciador<Pessoa> gerenciador) {
        super(gerenciador);
    }

    /**
     * Adiciona uma nova pessoa ao grupo, validando que o nome não seja vazio.
     *
     * @param pessoa A pessoa a ser adicionada.
     * @throws IllegalArgumentException se o nome da pessoa for vazio ou nulo.
     */
    public void adicionar(Pessoa pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da pessoa não pode ser vazio.");
        }
        gerenciador.adicionarEntidade(pessoa);
    }
}

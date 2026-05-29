package oikos.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Grupo;
import oikos.domain.model.Pessoa;

/**
 * Serviço de pessoas que especializa ServicoEntidades implementando
 * a validação específica ao cadastrar uma nova Pessoa em um grupo.
 */
@Service
public class ServicoPessoas extends ServicoEntidades<Pessoa> {

    /**
     * Cria o serviço vinculado ao serviço de grupos.
     *
     * @param servicoGrupos O serviço de grupos, usado para localizar o grupo.
     */
    public ServicoPessoas(ServicoGrupos servicoGrupos) {
        super(servicoGrupos);
    }
    
    protected Gerenciador<Pessoa> getGerenciadorPorGrupoId(UUID grupoId) {
        Grupo grupo = servicoGrupos.getGrupoPorId(grupoId);
        Gerenciador<Pessoa> gerenciador = grupo.getGerenciadorPessoas();

        return gerenciador;
    }

    /**
     * Adiciona uma nova pessoa ao grupo informado, validando que o nome não seja vazio.
     *
     * @param grupoId UUID do grupo.
     * @param pessoa A pessoa a ser adicionada.
     * @throws IllegalArgumentException se o nome da pessoa for vazio ou nulo.
     */
    public void adicionar(UUID grupoId, Pessoa pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da pessoa não pode ser vazio.");
        }

        Gerenciador<Pessoa> gerenciador = getGerenciadorPorGrupoId(grupoId);
        gerenciador.adicionarEntidade(pessoa);

        servicoGrupos.salvar();
    }
}

package oikos.domain.manager;

import oikos.domain.model.Pessoa;
import oikos.domain.model.Grupo;

/**
 * Gerenciador específico para a coleção de Pessoas de um Grupo.
 */
public class GerenciadorPessoas extends Gerenciador<Pessoa> {

	public GerenciadorPessoas(Grupo grupoOrigem) {
		super(grupoOrigem);
	}
	
}

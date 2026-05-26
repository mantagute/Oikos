package oikos.domain.manager;

import oikos.domain.model.Pessoa;
import oikos.domain.model.Grupo;

public class GerenciadorPessoas extends Gerenciador<Pessoa> {

	public GerenciadorPessoas(Grupo grupoOrigem) {
		super(grupoOrigem);
	}
	
}

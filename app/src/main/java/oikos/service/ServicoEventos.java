package oikos.service;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Evento;

/**
 * Serviço de eventos que especializa ServicoEntidades implementando
 * validações específicas ao cadastrar um novo Evento no grupo ativo.
 */
public class ServicoEventos extends ServicoEntidades<Evento> {

    /**
     * Cria o serviço vinculado ao serviço de grupos.
     *
     * @param servicoGrupos O serviço de grupos, que mantém o grupo ativo.
     */
    public ServicoEventos(ServicoGrupos servicoGrupos) {
        super(servicoGrupos);
    }

    @Override
    protected Gerenciador<Evento> getGerenciadorAtual() {
        return servicoGrupos.getGrupoSelecionado().getGerenciadorEventos();
    }

    /**
     * Adiciona um novo evento ao grupo ativo, validando nome e pontuação.
     *
     * @param evento O evento a ser adicionado.
     * @throws IllegalArgumentException se o nome for vazio ou os pontos forem menores ou iguais a zero.
     */
    public void adicionar(Evento evento) {
        if (evento.getNome() == null || evento.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do evento não pode ser vazio.");
        }
        if (evento.getPontos() <= 0) {
            throw new IllegalArgumentException("Pontos do evento devem ser maiores que zero.");
        }
        getGerenciadorAtual().adicionarEntidade(evento);
    }
}

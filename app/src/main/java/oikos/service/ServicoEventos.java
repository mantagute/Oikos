package oikos.service;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Evento;

/**
 * Serviço de eventos que especializa ServicoEntidades implementando
 * validações específicas ao cadastrar um novo Evento.
 */
public class ServicoEventos extends ServicoEntidades<Evento> {

    /**
     * Cria o serviço vinculado ao gerenciador de eventos do grupo.
     *
     * @param gerenciador O gerenciador que mantém a coleção de eventos.
     */
    public ServicoEventos(Gerenciador<Evento> gerenciador) {
        super(gerenciador);
    }

    /**
     * Adiciona um novo evento ao grupo, validando nome e pontuação.
     *
     * @param evento O evento a ser adicionado.
     * @throws IllegalArgumentException se o nome for vazio ou os pontos forem menores ou iguais a zero.
     */
    @Override
    public void adicionar(Evento evento) {
        if (evento.getNome() == null || evento.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do evento não pode ser vazio.");
        }
        if (evento.getPontos() <= 0) {
            throw new IllegalArgumentException("Pontos do evento devem ser maiores que zero.");
        }
        gerenciador.adicionarEntidade(evento);
    }
}

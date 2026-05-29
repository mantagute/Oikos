package oikos.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import oikos.domain.manager.Gerenciador;
import oikos.domain.model.Evento;
import oikos.domain.model.Grupo;

/**
 * Serviço de eventos que especializa ServicoEntidades implementando
 * validações específicas ao cadastrar um novo Evento em um grupo.
 */
@Service
public class ServicoEventos extends ServicoEntidades<Evento> {

    /**
     * Cria o serviço vinculado ao serviço de grupos.
     *
     * @param servicoGrupos O serviço de grupos, usado para localizar o grupo.
     */
    public ServicoEventos(ServicoGrupos servicoGrupos) {
        super(servicoGrupos);
    }

    protected Gerenciador<Evento> getGerenciadorPorGrupoId(UUID grupoId) {
        Grupo grupo = servicoGrupos.getGrupoPorId(grupoId);
        Gerenciador<Evento> gerenciador = grupo.getGerenciadorEventos();

        return gerenciador;
    }

    /**
     * Adiciona um novo evento ao grupo informado, validando nome e pontuação.
     *
     * @param grupoId UUID do grupo.
     * @param evento O evento a ser adicionado.
     * @throws IllegalArgumentException se o nome for vazio ou os pontos forem menores ou iguais a zero.
     */
    
    public void adicionar(UUID grupoId, Evento evento) {
        if (evento.getNome() == null || evento.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do evento não pode ser vazio.");
        }
        if (evento.getPontos() <= 0) {
            throw new IllegalArgumentException("Pontos do evento devem ser maiores que zero.");
        }

        Gerenciador<Evento> gerenciador = getGerenciadorPorGrupoId(grupoId);
        gerenciador.adicionarEntidade(evento);

        servicoGrupos.salvar();
    }
}

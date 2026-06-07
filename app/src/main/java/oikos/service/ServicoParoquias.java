package oikos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import oikos.persistence.PersistenciaJson;

import oikos.domain.model.Paroquia;
import oikos.domain.model.Grupo;


@Service
public class ServicoParoquias extends ServicoEscopoMaior<Paroquia> {
    private final ServicoGrupos servicoGrupos;


    private static PersistenciaJson<List<Paroquia>> criarPersistencia() {
        return new PersistenciaJson<List<Paroquia>>("../data/paroquias.json",new TypeReference<List<Paroquia>>() {}, () -> new ArrayList<>());
    }
    
    public ServicoParoquias(ServicoGrupos servicoGrupos) {
        super(criarPersistencia());
        this.servicoGrupos = servicoGrupos;
    }

    protected Paroquia instanciar(String nome, String senha) {
        return new Paroquia(nome, senha);
    }

    protected String getNome(Paroquia paroquia) {
        return paroquia.getNome();
    }

    public void vincularGrupo(UUID idParoquia, UUID idGrupo) {
        Paroquia paroquia = getPorId(idParoquia);
        Grupo grupo = servicoGrupos.getPorId(idGrupo);
        if (paroquia.getGerenciadorGrupos().getPorId(idGrupo) != null) {
            throw new IllegalArgumentException("Grupo já vinculado a esta paróquia");
        }
        paroquia.getGerenciadorGrupos().adicionarEntidade(grupo);
        salvar();
    }

    public void desvincularGrupo(UUID idParoquia, UUID idGrupo) {
        Paroquia paroquia = getPorId(idParoquia);
        paroquia.getGerenciadorGrupos().removerEntidade(idGrupo);
        salvar();
    }

}



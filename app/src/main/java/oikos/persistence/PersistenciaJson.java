package oikos.persistence;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import oikos.domain.interfaces.Persistivel;

/**
 * Implementacao de persistencia em arquivo JSON.
 *
 * @param <TipoDado> tipo do objeto persistido
 */
public class PersistenciaJson<TipoDado> implements Persistivel<TipoDado> {

    private final File arquivo;
    private final TypeReference<TipoDado> tipoDado;
    private final Supplier<TipoDado> valorPadrao;
    private final ObjectMapper mapper;

    public PersistenciaJson(String caminhoArquivo, TypeReference<TipoDado> tipoDado, Supplier<TipoDado> valorPadrao) {
        this.arquivo = new File(caminhoArquivo);
        this.tipoDado = tipoDado;
        this.valorPadrao = valorPadrao;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String salvar(TipoDado dados) {
        try {
            mapper.writeValue(arquivo, dados);
            return arquivo.getName();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados em arquivo JSON", e);
        }
    }

    public TipoDado recuperar() {
        if (!arquivo.exists() || arquivo.length() == 0) {
            return valorPadrao.get();
        }

        try {
            TipoDado dados = mapper.readValue(arquivo, tipoDado);
            return dados != null ? dados : valorPadrao.get();
        } catch (IOException e) {
            e.printStackTrace();
            return valorPadrao.get();
        }
    }
}

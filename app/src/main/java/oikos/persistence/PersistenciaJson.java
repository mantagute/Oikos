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

    /**
     * Configura a persistência com o caminho do arquivo, o tipo de referência
     * para desserialização e um fornecedor de valor padrão.
     *
     * @param caminhoArquivo Caminho do arquivo JSON para leitura/escrita.
     * @param tipoDado       TypeReference para orientar o Jackson na desserialização.
     * @param valorPadrao    Supplier que fornece o valor retornado quando o arquivo não existe ou está vazio.
     */
    public PersistenciaJson(String caminhoArquivo, TypeReference<TipoDado> tipoDado, Supplier<TipoDado> valorPadrao) {
        this.arquivo = new File(caminhoArquivo);
        this.tipoDado = tipoDado;
        this.valorPadrao = valorPadrao;
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Serializa os dados recebidos e grava no arquivo JSON configurado.
     *
     * @param dados Os dados a serem persistidos.
     * @return O nome do arquivo gravado.
     * @throws RuntimeException se ocorrer erro de escrita.
     */
    @Override
    public String salvar(TipoDado dados) {
        try {
            mapper.writeValue(arquivo, dados);
            return arquivo.getName();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados em arquivo JSON", e);
        }
    }

    /**
     * Lê o arquivo JSON configurado e desserializa seu conteúdo.
     * Retorna o valor padrão se o arquivo não existir, estiver vazio
     * ou não puder ser lido corretamente.
     *
     * @return Os dados recuperados ou o valor padrão.
     */
    @Override
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

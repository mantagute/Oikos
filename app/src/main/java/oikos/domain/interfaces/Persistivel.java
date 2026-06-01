package oikos.domain.interfaces;

/**
 * Permite salvar dados em um JSON e recuperá-los
 *
 * @param <TipoDado> tipo do dado persistido
 */
public interface Persistivel<TipoDado> {

    /**
     * Salva os dados em um JSON
     *
     * @param dados dados a serem salvos
     * @return identificador do recurso persistido
     */
    String salvar(TipoDado dados);

    /**
     * Recupera os dados de um JSON
     *
     * @return dados recuperados
     */
    TipoDado recuperar();
}

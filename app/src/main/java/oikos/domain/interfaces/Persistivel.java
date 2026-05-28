package oikos.domain.interfaces;

/**
 * Permite salvar dados em um JSON e recuperá-los
 */
public interface Persistivel {

    /**
     * Salva os dados em um JSON
     */
    String salvar();

    /**
     * Recupera os dados de um JSON
     */
    void recuperar();
}

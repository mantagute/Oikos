package oikos.domain.interfaces;

/**
 * Define o contrato de serialização para entidades que precisam ser persistidas.
 * Permite converter uma entidade para o formato JSON e reconstruí-la a partir dele.
 */
public interface Persistivel {

    /**
     * Serializa o estado atual do objeto para uma string no formato JSON.
     *
     * @return Uma {@code String} representando o objeto em formato JSON.
     */
    String toJson();

    /**
     * Restaura o estado do objeto a partir de uma string no formato JSON.
     *
     * @param json A string JSON contendo os dados para popular o objeto.
     */
    void fromJson(String json);
}

package oikos.domain.interfaces;

/**
 * Define o contrato de pontuação para entidades que participam do sistema de gamificação.
 * Qualquer classe que implemente esta interface é capaz de acumular e reiniciar pontos.
 */
public interface Pontuavel {

    /**
     * Retorna a pontuação acumulada atualmente.
     *
     * @return O total de pontos acumulados como {@code int}.
     */
    int getPontuacaoAtual();

    /**
     * Incrementa a pontuação pelo valor especificado.
     *
     * @param pontos A quantidade de pontos a ser adicionada. Deve ser um valor positivo.
     */
    void adicionarPontos(int pontos);

    /**
     * Reinicia a pontuação, zerando todos os pontos acumulados.
     */
    void reiniciarPontos();

    void setPontuacaoAtual(int valor);
}

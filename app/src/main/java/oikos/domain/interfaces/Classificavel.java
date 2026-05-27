package oikos.domain.interfaces;

/**
 * Gera uma classificação para um grupo a partir do numero de metas já batidas
 */
public interface Classificavel {

    /**
     * Retorna a classificação do grupo com base no número de metas batidas.
     *
     * @param metasBatidas número de vezes que a meta já foi batida pelo grupo
     * @return A classificação do grupo (e.g., "Bronze", "Prata", "Ouro").
     */
    default String classificar(int metasBatidas) 
    {
        if (metasBatidas >= 10) {return "Diamante";}
        else if (metasBatidas >= 5) {return "Ouro";}
        else if (metasBatidas >= 3) {return "Prata";}
        else if (metasBatidas >= 1) {return "Bronze";}
        else {return "Iniciante";}
    }
}

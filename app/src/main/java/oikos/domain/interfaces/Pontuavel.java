package oikos.domain.interfaces;

public interface Pontuavel {
    
    int getPontuacaoAtual();

    void adicionarPontos(int pontos);

    void reiniciarPontos();
}


package oikos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada principal da aplicação Oikos.
 * Esta classe inicializa o contexto do Spring Boot e sobe o servidor embutido.
 */
@SpringBootApplication
public class OikosApplication {

    /**
     * Método principal que inicializa a aplicação Spring Boot.
     *
     * @param args Argumentos de linha de comando passados na inicialização.
     */
    public static void main(String[] args) {
        SpringApplication.run(OikosApplication.class, args);
    }
}

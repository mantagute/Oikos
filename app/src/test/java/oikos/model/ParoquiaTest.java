package oikos.model;

import oikos.domain.model.Paroquia;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParoquiaTest {

    @Test
    void autenticarSenhaCorreta() {
        Paroquia paroquia = new Paroquia("Paróquia Teste", "senha123");
        assertTrue(paroquia.autenticarSenha("senha123"));
    }

    @Test
    void autenticarSenhaIncorreta() {
        Paroquia paroquia = new Paroquia("Paróquia Teste", "senha123");
        assertFalse(paroquia.autenticarSenha("senhaErrada"));
    }

    @Test
    void getNomeRetornaNomeCorreto() {
        Paroquia paroquia = new Paroquia("Paróquia Teste", "1234");
        assertEquals("Paróquia Teste", paroquia.getNome());
    }

    @Test
    void setNomeAlteraNome() {
        Paroquia paroquia = new Paroquia("Original", "1234");
        paroquia.setNome("Novo Nome");
        assertEquals("Novo Nome", paroquia.getNome());
    }

    @Test
    void paroquiaPossuiIdUnico() {
        Paroquia paroquia1 = new Paroquia("A", "1");
        Paroquia paroquia2 = new Paroquia("B", "2");
        assertNotNull(paroquia1.getId());
        assertNotNull(paroquia2.getId());
        assertNotEquals(paroquia1.getId(), paroquia2.getId());
    }
}

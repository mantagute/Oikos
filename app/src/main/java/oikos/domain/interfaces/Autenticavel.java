package oikos.domain.interfaces;

/**
 * Define o contrato de autenticação para entidades protegidas por senha.
 * Qualquer classe que implemente esta interface deve ser capaz de verificar
 * credenciais de acesso.
 */
public interface Autenticavel {

    /**
     * Verifica se a senha fornecida corresponde à senha cadastrada.
     *
     * @param senha A senha a ser verificada.
     * @return {@code true} se a senha estiver correta, {@code false} caso contrário.
     */
    boolean autenticarSenha(String senha);
}

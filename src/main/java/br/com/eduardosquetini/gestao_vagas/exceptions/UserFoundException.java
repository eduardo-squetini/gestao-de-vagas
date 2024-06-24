package br.com.eduardosquetini.gestao_vagas.exceptions;

// Declaração da classe 'UserFoundException' que estende 'RuntimeException'.
public class UserFoundException extends RuntimeException {

    // Construtor da classe 'UserFoundException'.
    public UserFoundException() {
        // Chama o construtor da classe pai ('RuntimeException') passando a mensagem "Usuário já existe".
        super("Usuário já existe");
    }

}

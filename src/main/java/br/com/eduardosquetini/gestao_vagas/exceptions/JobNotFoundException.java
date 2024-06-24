package br.com.eduardosquetini.gestao_vagas.exceptions;

// Declaração da classe 'JobNotFoundException' que estende 'RuntimeException'.
public class JobNotFoundException extends RuntimeException {

    // Construtor da classe 'JobNotFoundException'.
    public JobNotFoundException() {
        // Chama o construtor da classe pai ('RuntimeException') passando a mensagem "Job not found".
        super("Job not found");
    }

}

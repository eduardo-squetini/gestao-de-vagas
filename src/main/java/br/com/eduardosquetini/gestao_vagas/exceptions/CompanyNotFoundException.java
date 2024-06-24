// Declara o pacote ao qual a classe pertence.
package br.com.eduardosquetini.gestao_vagas.exceptions;

// Declaração da classe 'CompanyNotFoundException' que estende 'RuntimeException'.
public class CompanyNotFoundException extends RuntimeException {

    // Construtor da classe 'CompanyNotFoundException'.
    public CompanyNotFoundException() {
        // Chama o construtor da classe pai ('RuntimeException') passando a mensagem "Company not found".
        super("Company not found");
    }

}
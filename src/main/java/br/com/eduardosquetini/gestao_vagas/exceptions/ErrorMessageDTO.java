package br.com.eduardosquetini.gestao_vagas.exceptions;

// Importa as anotações 'AllArgsConstructor' e 'Data' da biblioteca Lombok.
import lombok.AllArgsConstructor;
import lombok.Data;

// Anotação Lombok que gera automaticamente métodos getters, setters, toString, equals e hashCode.
@Data
// Anotação Lombok que gera automaticamente um construtor com todos os argumentos.
@AllArgsConstructor
// Declaração da classe 'ErrorMessageDTO'.
public class ErrorMessageDTO {

    // Declaração do campo 'message' do tipo String.
    private String message;
    
    // Declaração do campo 'field' do tipo String.
    private String field;

}

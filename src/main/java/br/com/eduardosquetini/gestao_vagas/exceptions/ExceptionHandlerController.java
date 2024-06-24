package br.com.eduardosquetini.gestao_vagas.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* Annotation para tratamento de exceções */
@ControllerAdvice
public class ExceptionHandlerController {

    // Fonte de mensagens para internacionalização.
    private MessageSource messageSource;

    // Construtor da classe, inicializando a fonte de mensagens.
    public ExceptionHandlerController(MessageSource message) {
        this.messageSource = message;
    }

    // Método que lida com a exceção 'MethodArgumentNotValidException'.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        // Lista para armazenar os erros.
        List<ErrorMessageDTO> dto = new ArrayList<>();

        // Itera sobre os erros de campo e processa cada um.
        e.getBindingResult().getFieldErrors().forEach(err -> {
           
            // Obtém a mensagem de erro correspondente ao campo, levando em conta a localização.
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            // Cria um objeto 'ErrorMessageDTO' com a mensagem e o campo do erro.
            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
            // Adiciona o objeto 'ErrorMessageDTO' à lista de erros.
            dto.add(error);

        });

        // Retorna a lista de erros encapsulada em um 'ResponseEntity' com status HTTP 400 (BAD REQUEST).
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

}

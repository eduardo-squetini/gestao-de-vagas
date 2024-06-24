package br.com.eduardosquetini.gestao_vagas.modules.candidate.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data // Anotação do Lombok para gerar métodos getters, setters, toString, equals e hashCode.
@Entity(name = "candidate") // Anotação JPA para indicar que esta classe é uma entidade e mapeá-la para uma tabela de banco de dados chamada 'candidate'.
public class CandidateEntity {

    @Id // Anotação JPA para marcar o campo abaixo como a chave primária da entidade.
    @GeneratedValue(strategy = GenerationType.UUID) // Anotação JPA para especificar que o ID deve ser gerado automaticamente usando a estratégia UUID.
    private UUID id; // Identificador único para cada entidade candidato.

    @Schema(example = "Lucas Guimarães", requiredMode = RequiredMode.REQUIRED, description = "Nome do candidato") // Anotação OpenAPI para descrever o campo na documentação da API.
    private String name; // Nome do candidato.

    @NotBlank() // Anotação de Validação de Bean para garantir que este campo não esteja em branco.
    @Pattern(regexp = "\\S+", message = "o campo [username] não deve conter espaço") // Anotação de Validação de Bean para garantir que este campo não contenha espaços.
    @Schema(example = "Lucas_GG", requiredMode = RequiredMode.REQUIRED, description = "Username do candidato") // Anotação OpenAPI para documentação da API.
    private String username; // Nome de usuário do candidato.

    @Email(message = "O campo [email] deve conter um e-mail válido") // Anotação de Validação de Bean para garantir que este campo contenha um endereço de e-mail válido.
    @Schema(example = "lucasguimaraes@gmail.com", requiredMode = RequiredMode.REQUIRED, description = "Email do candidato") // Anotação OpenAPI para documentação da API.
    private String email; // Endereço de e-mail do candidato.

    @Length(min = 10, max = 100, message = "A senha deve conter entre 10 e 100 caracteres") // Anotação de Validação de Bean para especificar o comprimento da senha.
    @Schema(example = "012345678955", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Senha do candidato") // Anotação OpenAPI para documentação da API.
    private String password; // Senha do candidato.

    @Schema(example = "Desenvolvedor Python", requiredMode = RequiredMode.REQUIRED, description = "Breve descrição do candidato") // Anotação OpenAPI para documentação da API.
    private String description; // Breve descrição do candidato.

    private String curriculum; // Currículo vitae do candidato.

    @CreationTimestamp // Anotação Hibernate para definir automaticamente este campo com a data e hora atual ao criar uma entidade.
    private LocalDateTime createdAt; // A data e hora em que a entidade candidato foi criada.

}


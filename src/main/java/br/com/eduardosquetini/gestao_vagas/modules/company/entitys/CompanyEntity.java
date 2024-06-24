package br.com.eduardosquetini.gestao_vagas.modules.company.entitys;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Company") // Anotação do Hibernate que marca a classe como uma entidade e mapeia para a tabela 'Company'.
@Data // Anotação do Lombok que gera automaticamente getters, setters, toString, equals e hashCode.
@Builder // Anotação do Lombok que fornece um padrão de construção para a classe.
@NoArgsConstructor // Anotação do Lombok que gera um construtor sem argumentos.
@AllArgsConstructor // Anotação do Lombok que gera um construtor com todos os argumentos.
public class CompanyEntity {

    @Id // Anotação do Hibernate que marca o campo como chave primária.
    @GeneratedValue(strategy = GenerationType.UUID) // Anotação do Hibernate que especifica a estratégia de geração automática do ID como UUID.
    private UUID id; // Identificador único da empresa.

    // Validação para garantir que o nome de usuário não contenha espaços.
    @NotBlank() // Anotação de validação que garante que o campo não esteja em branco.
    @Pattern(regexp = "\\S+", message = "o campo [username] não deve conter espaço") // Anotação de validação que aplica uma expressão regular para verificar se não há espaços.
    private String username; // Nome de usuário da empresa.

    // Validação para garantir que o e-mail seja válido.
    @Email(message = "O campo [email] deve conter um e-mail válido") // Anotação de validação que garante que o campo seja um e-mail válido.
    private String email; // E-mail da empresa.

    // Validação para garantir que a senha tenha entre 10 e 100 caracteres.
    @Length(min = 10, max = 100, message = "A senha deve conter entre 10 e 100 caracteres") // Anotação de validação que define o tamanho mínimo e máximo da senha.
    private String password; // Senha da empresa.

    private String name; // Nome da empresa.
    private String website; // Site da empresa.
    private String description; // Descrição da empresa.

    @CreationTimestamp // Anotação do Hibernate que marca o campo para ser preenchido com a data e hora atual quando a entidade é criada.
    private LocalDateTime createdAt; // Data e hora de criação da empresa.
}
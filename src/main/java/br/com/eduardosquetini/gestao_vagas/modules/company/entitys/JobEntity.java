package br.com.eduardosquetini.gestao_vagas.modules.company.entitys;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "job") // Anotação do Hibernate que marca a classe como uma entidade e mapeia para a tabela 'job'.
@Data // Anotação do Lombok que gera automaticamente getters, setters, toString, equals e hashCode.
@Builder // Anotação do Lombok que fornece um padrão de construção para a classe.
@AllArgsConstructor // Anotação do Lombok que gera um construtor com todos os argumentos.
@NoArgsConstructor // Anotação do Lombok que gera um construtor sem argumentos.
public class JobEntity {

    @Id // Anotação do Hibernate que marca o campo como chave primária.
    @GeneratedValue(strategy = GenerationType.UUID) // Anotação do Hibernate que especifica a estratégia de geração automática do ID como UUID.
    private UUID id; // Identificador único da vaga de emprego.

    @Schema(example="Vaga para design") // Anotação do Swagger para documentar o campo 'description' no DTO.
    private String description; // Descrição da vaga de emprego.

    @Schema(example="GYMPass, Plano de Saúde") // Anotação do Swagger para documentar o campo 'benefits' no DTO.
    private String benefits; // Benefícios oferecidos na vaga de emprego.

    @NotBlank(message = "Esse campo é obrigatório") // Anotação de validação que garante que o campo não esteja em branco.
    @Schema(example="SENIOR") // Anotação do Swagger para documentar o campo 'level' no DTO.
    private String level; // Nível da vaga de emprego (ex: Júnior, Pleno, Sênior).

    /* Definindo relacionamento de tabela: */

    /* many jobs for one company */
    @ManyToOne() // Anotação do Hibernate que define um relacionamento muitos-para-um com a entidade CompanyEntity.
    @JoinColumn(name = "company_id", insertable = false, updatable = false) // Anotação do Hibernate que define a coluna de junção para o relacionamento.
    private CompanyEntity companyEntity; // A empresa associada à vaga de emprego.

    @Column(name = "company_id" , nullable = false) // Anotação do Hibernate que mapeia o campo para a coluna 'company_id' na tabela e define como não nulo.
    private UUID companyId; // O ID da empresa associada à vaga de emprego.

    @CreationTimestamp // Anotação do Hibernate que marca o campo para ser preenchido com a data e hora atual quando a entidade é criada.
    private LocalDateTime createdAt; // Data e hora de criação da vaga de emprego.

}
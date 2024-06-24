package br.com.eduardosquetini.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Anotação do Lombok que gera automaticamente getters, setters, toString, equals e hashCode.
@Builder // Anotação do Lombok que fornece um padrão de construção para a classe.
@NoArgsConstructor // Anotação do Lombok que gera um construtor sem argumentos.
@AllArgsConstructor // Anotação do Lombok que gera um construtor com todos os argumentos.
public class CreateJobDTO {
    
    @Schema(example = "Vaga para pessoa desenvolvedora júnior", requiredMode = RequiredMode.REQUIRED) // Anotação do Swagger para documentar o campo 'description' no DTO.
    private String description; // Descrição da vaga de emprego.

    @Schema(example = "GYMPASS, Plano de Saúde", requiredMode = RequiredMode.REQUIRED) // Anotação do Swagger para documentar o campo 'benefits' no DTO.
    private String benefits; // Benefícios oferecidos na vaga de emprego.

    @Schema(example = "JÚNIOR", requiredMode = RequiredMode.REQUIRED) // Anotação do Swagger para documentar o campo 'level' no DTO.
    private String level; // Nível da vaga de emprego (ex: Júnior, Pleno, Sênior).

}
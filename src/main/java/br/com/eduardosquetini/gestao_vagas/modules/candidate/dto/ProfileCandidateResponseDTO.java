package br.com.eduardosquetini.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {
    // A anotação @Schema é usada para fornecer informações sobre a estrutura do objeto.
    // Ela não afeta o comportamento do código em tempo de execução.
    
    // Descrição do candidato, exemplo: "Desenvolvedor Java"
    @Schema(example = "Desenvolvedor Java")
    private String description;

    // Nome de usuário, exemplo: "lucas_2005"
    @Schema(example = "lucas_2005")
    private String username;

    // Endereço de e-mail, exemplo: "lucas@gmail.com"
    @Schema(example = "lucas@gmail.com")
    private String email;

    // Nome completo do candidato, exemplo: "Lucas Araújo"
    @Schema(example = "Lucas Araújo")
    private String name;

    // ID único do candidato (geralmente um UUID)
    private UUID id;
}

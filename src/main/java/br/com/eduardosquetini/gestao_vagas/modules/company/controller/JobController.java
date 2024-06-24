package br.com.eduardosquetini.gestao_vagas.modules.company.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardosquetini.gestao_vagas.modules.company.UseCases.CreateJobUseCase;
import br.com.eduardosquetini.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController // Anotação que marca a classe como um controlador REST, que manipula solicitações HTTP.
@RequestMapping("/company/job") // Anotação que define o caminho base para todas as solicitações de vagas de emprego manipuladas por este controlador.
public class JobController {

    // Injeta uma instância de CreateJobUseCase para lidar com a lógica de criação de vagas de emprego.
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/") // Anotação que mapeia solicitações POST para '/company/job/' para este método.
    @PreAuthorize("hasRole('COMPANY')") // Anotação que especifica que apenas usuários com a role 'COMPANY' podem acessar este método.
    @Tag(name = "Vagas", description = "Informações das vagas") // Anotação do Swagger para agrupar operações com a tag 'Vagas'.
    @Operation(summary = "Cadastro de vagas", description = "Essa Função é responsável por cadastrar as vagas dentro da empresa") // Anotação do Swagger para descrever a operação de cadastro de vagas.
    @ApiResponses({ // Anotação do Swagger para descrever as respostas possíveis da operação.
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class)) // Descreve o conteúdo da resposta com código 200 e o schema correspondente.
            })
    })
    @SecurityRequirement(name = "jwt_auth") // Anotação do Swagger para indicar que esta operação requer autenticação JWT.
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO CreateJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id"); // Recupera o ID da empresa do atributo da solicitação.

        try {
            // Constrói uma entidade JobEntity com os dados recebidos e o ID da empresa.
            var jobEntity = JobEntity.builder()
                    .companyId(UUID.fromString(companyId.toString()))
                    .benefits(CreateJobDTO.getBenefits())
                    .description(CreateJobDTO.getDescription())
                    .level(CreateJobDTO.getLevel())
                    .build();

            // Executa o caso de uso de criação de vaga e obtém o resultado.
            var result = this.createJobUseCase.execute(jobEntity);
            // Se bem-sucedido, retorna uma resposta HTTP 200 OK com o resultado no corpo.
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            // Se ocorrer uma exceção, retorna uma resposta HTTP 400 Bad Request com a mensagem de erro no corpo.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
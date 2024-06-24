package br.com.eduardosquetini.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardosquetini.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases.ApplyJobCandidateUseCase;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases.CreateCandidateUseCase;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases.ListAllJobsByFilterUseCase;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases.ProfileCandidateUsecase;
import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

// Anotação que define esta classe como um controlador REST.
@RestController
// Anotação que define o mapeamento de requisições para "/candidate".
@RequestMapping("/candidate")
// Anotação que documenta a classe com um nome e descrição para a API.
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    // Injeta a dependência 'CreateCandidateUseCase' automaticamente.
    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    // Injeta a dependência 'ProfileCandidateUsecase' automaticamente.
    @Autowired
    private ProfileCandidateUsecase profileCandidateUsecase;

    // Injeta a dependência 'ListAllJobsByFilterUseCase' automaticamente.
    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    // Injeta a dependência 'ApplyJobCandidateUseCase' automaticamente.
    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    // Mapeia requisições HTTP POST para o caminho "/".
    @PostMapping("/")
    // Documenta a operação de cadastro de candidato com um resumo e descrição.
    @Operation(summary = "Cadastro de Candidato", description = "Essa Função é responsável por cadastrar um candidato")
    // Define as respostas esperadas para a operação.
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    // Método que lida com o cadastro de candidato, recebendo um objeto 'CandidateEntity' no corpo da requisição.
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Mapeia requisições HTTP GET para o caminho "/".
    @GetMapping("/")
    // Define que o método requer autorização e que o usuário deve ter o papel 'CANDIDATE'.
    @PreAuthorize("hasRole('CANDIDATE')")
    // Documenta a operação de busca de perfil de candidato com um resumo e descrição.
    @Operation(summary = "Perfil do Candidato", description = "Essa Função é responsável por buscar as informações do perfil do candidato")
    // Define que a operação requer autenticação JWT.
    @SecurityRequirement(name = "jwt_auth")
    // Define as respostas esperadas para a operação.
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    // Método que lida com a busca de perfil de candidato, recebendo a requisição HTTP.
    public ResponseEntity<Object> get(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUsecase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Mapeia requisições HTTP GET para o caminho "/job".
    @GetMapping("/job")
    // Define que o método requer autorização e que o usuário deve ter o papel 'CANDIDATE'.
    @PreAuthorize("hasRole('CANDIDATE')")
    // Documenta a operação de listagem de vagas com um resumo e descrição.
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Essa Função é responsável por listar todas as vagas disponíveis, baseada no filtro")
    // Define as respostas esperadas para a operação.
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    })
    // Define que a operação requer autenticação JWT.
    @SecurityRequirement(name = "jwt_auth")
    // Método que lida com a listagem de vagas, recebendo um parâmetro de filtro.
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    // Mapeia requisições HTTP POST para o caminho "/job/apply".
    @PostMapping("/job/apply")
    // Define que o método requer autorização e que o usuário deve ter o papel 'CANDIDATE'.
    @PreAuthorize("hasRole('CANDIDATE')")
    // Define que a operação requer autenticação JWT.
    @SecurityRequirement(name = "jwt_auth")
    // Documenta a operação de inscrição em vagas com um resumo e descrição.
    @Operation(summary = "Inscrição de vagas", description = "Essa Função é responsável por realizar a inscrição do candidato em uma vaga.")
    // Método que lida com a inscrição em vagas, recebendo a requisição HTTP e o ID da vaga.
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
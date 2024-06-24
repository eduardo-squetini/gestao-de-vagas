package br.com.eduardosquetini.gestao_vagas.modules.candidate.useCases;

import br.com.eduardosquetini.gestao_vagas.exceptions.JobNotFoundException;
import br.com.eduardosquetini.gestao_vagas.exceptions.UserNotfoundException;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases.ApplyJobCandidateUseCase;
import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.JobEntity;
import br.com.eduardosquetini.gestao_vagas.modules.company.repositories.JobRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // Anotação do JUnit 5 que habilita as extensões do Mockito para inicialização de mocks e injeção de dependências.
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks // Anotação do Mockito que cria instâncias da classe e injeta os mocks marcados com @Mock nela.
    private ApplyJobCandidateUseCase applyJobCandidateUseCase; // Instância da classe de caso de uso que está sendo testada.

    @Mock // Anotação do Mockito que cria um mock simulado para a dependência.
    private CandidateRepository candidateRepository; // Mock do repositório de candidatos.

    @Mock // Anotação do Mockito que cria um mock simulado para a dependência.
    private JobRepository jobRepository; // Mock do repositório de vagas de emprego.

    @Mock // Anotação do Mockito que cria um mock simulado para a dependência.
    private ApplyJobRepository applyJobRepository; // Mock do repositório de aplicações para vagas de emprego.

    @Test // Anotação do JUnit que marca o método como um teste.
    @DisplayName("Should not be able to apply job with candidate not found") // Define um nome descritivo para o teste.
    public void should_not_be_able_to_aply_job_with_candidate_not_found() {
        try {
            applyJobCandidateUseCase.execute(null, null); // Executa o caso de uso com parâmetros nulos para simular a não localização do candidato.
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(UserNotfoundException.class); // Verifica se a exceção lançada é do tipo esperado.
      }
    }

    @Test // Anotação do JUnit que marca o método como um teste.
    public void should_not_be_able_to_aply_job_with_job_not_found(){
        var idCandidate = UUID.randomUUID(); // Gera um UUID aleatório para o ID do candidato.

        var candidate = new CandidateEntity(); // Cria uma nova entidade de candidato.
        candidate.setId(idCandidate); // Define o ID do candidato na entidade.

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate)); // Configura o mock para retornar um Optional com o candidato quando o método findById for chamado com o ID do candidato.

        try {
            applyJobCandidateUseCase.execute(idCandidate, null); // Executa o caso de uso com ID do candidato e ID da vaga nulo para simular a não localização da vaga.
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(JobNotFoundException.class); // Verifica se a exceção lançada é do tipo esperado.
      }
    }

    @Test // Anotação do JUnit que marca o método como um teste.
    public void should_be_able_to_create_a_new_apply_job(){
        var idCandidate = UUID.randomUUID(); // Gera um UUID aleatório para o ID do candidato.
        var idJob = UUID.randomUUID(); // Gera um UUID aleatório para o ID da vaga.

        var applyJob = ApplyJobEntity.builder() // Cria uma nova entidade de aplicação para vaga usando o padrão Builder.
        .candidateId(idCandidate)
        .jobId(idJob)
        .build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build(); // Cria uma nova entidade de aplicação para vaga com ID usando o padrão Builder.

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity())); // Configura o mock para retornar um Optional com uma nova entidade de candidato quando o método findById for chamado com o ID do candidato.
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity())); // Configura o mock para retornar um Optional com uma nova entidade de vaga quando o método findById for chamado com o ID da vaga.

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated); // Configura o mock para retornar a entidade de aplicação criada quando o método save for chamado com a entidade de aplicação.

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob); // Executa o caso de uso com os IDs gerados.

        Assertions.assertThat(result).hasFieldOrProperty("id"); // Verifica se o resultado tem uma propriedade 'id'.
        assertNotNull(result.getId());
     }
     } 
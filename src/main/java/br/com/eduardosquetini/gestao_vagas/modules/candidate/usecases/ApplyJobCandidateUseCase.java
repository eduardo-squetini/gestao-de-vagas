package br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardosquetini.gestao_vagas.exceptions.JobNotFoundException;
import br.com.eduardosquetini.gestao_vagas.exceptions.UserNotfoundException;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.eduardosquetini.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    // ID Candidato
    // ID Vaga
    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        //Validar se candidato existe
        this.candidateRepository.findById(idCandidate)
        .orElseThrow(()->{
            throw new UserNotfoundException();
        });

        //Validar se a vaga existe
        this.jobRepository.findById(idJob)
        .orElseThrow(()-> {
            throw new JobNotFoundException();
        });

        //Candidato se inscrever na vaga
        var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob).build();

        applyJob = applyJobRepository.save(applyJob);
        return applyJob;


    }

}

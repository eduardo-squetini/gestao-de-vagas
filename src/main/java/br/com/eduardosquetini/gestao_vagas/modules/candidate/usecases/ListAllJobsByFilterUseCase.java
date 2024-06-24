package br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.JobEntity;
import br.com.eduardosquetini.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    //filtro de vagas para o candidato
    public List<JobEntity> execute(String filter){
       return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }

}

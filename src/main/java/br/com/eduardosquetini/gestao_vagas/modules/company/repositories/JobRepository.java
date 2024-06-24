package br.com.eduardosquetini.gestao_vagas.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    
    //"contains" - LIKE
    // Select * from job where description like %filter%

    List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);


}

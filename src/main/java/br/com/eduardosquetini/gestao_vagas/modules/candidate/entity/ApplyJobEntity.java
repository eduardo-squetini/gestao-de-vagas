package br.com.eduardosquetini.gestao_vagas.modules.candidate.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.JobEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "apply_jobs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobEntity {
    // Classe que representa a entidade de candidatura a vagas de emprego.

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    // Campo para o ID único da candidatura (geralmente um UUID).

    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    private CandidateEntity candidateEntity;
    // Relação muitos-para-um com a entidade de candidato (CandidateEntity).
    // A anotação @JoinColumn especifica o nome da coluna de chave estrangeira no banco de dados.

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    private JobEntity jobEntity;
    // Relação muitos-para-um com a entidade de vaga de emprego (JobEntity).

    @Column(name = "candidate_id")
    private UUID candidateId;
    // Campo para armazenar o UUID do candidato associado à candidatura.

    @Column(name = "job_id")
    private UUID jobId;
    // Campo para armazenar o UUID da vaga de emprego associada à candidatura.

    @CreationTimestamp
    private LocalDateTime createdAt;
    // Campo para armazenar a data e hora de criação da candidatura.
}

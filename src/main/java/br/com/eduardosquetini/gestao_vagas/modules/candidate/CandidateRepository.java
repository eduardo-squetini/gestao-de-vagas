package br.com.eduardosquetini.gestao_vagas.modules.candidate;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eduardosquetini.gestao_vagas.modules.candidate.entity.CandidateEntity;

// Interface para o repositório de candidatos, estendendo JpaRepository para fornecer operações CRUD para a entidade CandidateEntity.
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    // Método para encontrar um candidato pelo nome de usuário. Retorna um Optional
    // que pode conter a entidade CandidateEntity se encontrada.
    Optional<CandidateEntity> findByUsername(String username);

    // Método para encontrar um candidato pelo e-mail. Retorna um Optional que pode
    // conter a entidade CandidateEntity se encontrada.
    Optional<CandidateEntity> findByEmail(String email);
}
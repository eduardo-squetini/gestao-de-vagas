package br.com.eduardosquetini.gestao_vagas.modules.company.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity> findByUsername(String username);

    Optional<CompanyEntity> findByEmail(String email);

}

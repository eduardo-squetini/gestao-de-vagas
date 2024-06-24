package br.com.eduardosquetini.gestao_vagas.modules.company.UseCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardosquetini.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.JobEntity;
import br.com.eduardosquetini.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.eduardosquetini.gestao_vagas.modules.company.repositories.JobRepository;

@Service // Anotação do Spring que marca a classe como um serviço, que é um componente especializado para realizar lógica de negócios.
public class CreateJobUseCase {

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private JobRepository jobRepository; // Repositório para operações de banco de dados relacionadas a vagas de emprego.

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private CompanyRepository companyRepository; // Repositório para operações de banco de dados relacionadas a empresas.

    public JobEntity execute(JobEntity jobEntity){
        // Verifica se a empresa associada à vaga existe, lança uma exceção se não existir.
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFoundException();
        });
        // Salva a entidade da vaga no banco de dados e retorna-a.
        return this.jobRepository.save(jobEntity);
    }
    
}

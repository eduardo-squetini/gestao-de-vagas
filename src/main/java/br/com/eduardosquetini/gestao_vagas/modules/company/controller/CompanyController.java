package br.com.eduardosquetini.gestao_vagas.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardosquetini.gestao_vagas.modules.company.UseCases.CreateCompanyUseCase;
import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.CompanyEntity;
import jakarta.validation.Valid;

@RestController // Anotação que marca a classe como um controlador REST, que manipula solicitações HTTP.
@RequestMapping("/company") // Anotação que define o caminho base para todas as solicitações manipuladas por este controlador.
public class CompanyController {
    
    // Injeta uma instância de CreateCompanyUseCase para lidar com a lógica de criação de empresas.
    @Autowired
    private CreateCompanyUseCase CreateCompanyUseCase;

    @PostMapping("/") // Anotação que mapeia solicitações POST para '/company/' para este método.
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity){
        try {
            // Executa o caso de uso de criação da empresa e obtém o resultado.
            var result = this.CreateCompanyUseCase.execute(companyEntity);
            // Se bem-sucedido, retorna uma resposta HTTP 200 OK com o resultado no corpo.
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            // Se ocorrer uma exceção, retorna uma resposta HTTP 400 Bad Request com a mensagem de erro no corpo.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
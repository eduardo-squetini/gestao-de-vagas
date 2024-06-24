package br.com.eduardosquetini.gestao_vagas.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardosquetini.gestao_vagas.modules.company.UseCases.AuthCompanyUseCase;
import br.com.eduardosquetini.gestao_vagas.modules.company.dto.AuthCompanyDTO;

@RestController // Anotação que marca a classe como um controlador REST, que manipula solicitações HTTP.
@RequestMapping("/company") // Anotação que define o caminho base para todas as solicitações manipuladas por este controlador.
public class AuthCompanyController {

    // Injeta uma instância de AuthCompanyUseCase para lidar com a lógica de autenticação da empresa.
    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/auth") // Anotação que mapeia solicitações POST para '/company/auth' para este método.
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
            // Executa o caso de uso de autenticação da empresa e obtém o resultado.
            var result = this.authCompanyUseCase.execute(authCompanyDTO);
            // Se bem-sucedido, retorna uma resposta HTTP 200 OK com o resultado no corpo.
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            // Se ocorrer uma exceção, retorna uma resposta HTTP 401 Não Autorizado com a mensagem de erro no corpo.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

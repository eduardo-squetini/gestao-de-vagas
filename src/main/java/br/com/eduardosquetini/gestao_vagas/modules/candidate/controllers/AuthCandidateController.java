package br.com.eduardosquetini.gestao_vagas.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardosquetini.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases.AuthCandidateUseCase;

// Anotação que define esta classe como um controlador REST.
@RestController
// Anotação que define o mapeamento de requisições para "/candidate".
@RequestMapping("/candidate")
public class AuthCandidateController {

    // Injeta a dependência 'AuthCandidateUseCase' automaticamente.
    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    // Mapeia requisições HTTP POST para o caminho "/auth".
    @PostMapping("/auth")
    // Define um método que lida com a autenticação do candidato, recebendo um objeto 'AuthCandidateRequestDTO' no corpo da requisição.
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {

        try {
            // Executa o caso de uso de autenticação e obtém um token.
            var token = this.authCandidateUseCase.execute(authCandidateRequestDTO);
            // Retorna uma resposta HTTP 200 (OK) com o token no corpo da resposta.
            return ResponseEntity.ok().body(token);

        } catch (Exception e) {
            // Em caso de exceção, retorna uma resposta HTTP 401 (UNAUTHORIZED) com a mensagem de erro no corpo da resposta.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}

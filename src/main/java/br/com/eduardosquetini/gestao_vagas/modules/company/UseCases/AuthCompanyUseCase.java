package br.com.eduardosquetini.gestao_vagas.modules.company.UseCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.eduardosquetini.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.eduardosquetini.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.eduardosquetini.gestao_vagas.modules.company.repositories.CompanyRepository;

// Anotação do Spring que marca a classe como um serviço, que é um componente especializado para realizar lógica de negócios.
@Service 
public class AuthCompanyUseCase {

    @Value("${security.token.secret}") // Anotação do Spring que injeta o valor da propriedade 'security.token.secret' do arquivo de configuração.
    private String secretKey; // Chave secreta usada para assinar o token JWT.

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private CompanyRepository companyRepository; // Repositório para operações de banco de dados relacionadas a empresas.

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private PasswordEncoder passwordEncoder; // Codificador de senha para verificar senhas criptografadas.

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        /*
         * Verifica se a empresa existe pelo nome de usuário e lança uma exceção se não existir.
         */
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username/password incorrect")); // Busca a empresa pelo nome de usuário e lança uma exceção se não encontrada.

        // Verifica se as senhas são iguais.
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword()); // Compara a senha fornecida com a senha armazenada.

        // Se as senhas não forem iguais, lança uma exceção de autenticação.
        if (!passwordMatches) {
            throw new AuthenticationException("Password does not match");
        }

        // Se as senhas forem iguais, gera o token JWT.
        Algorithm algorithm = Algorithm.HMAC256(secretKey); // Define o algoritmo de assinatura do token.

        var expiresIn = Instant.now().plus(Duration.ofHours(2)); // Define o tempo de expiração do token.

        var token = JWT.create().withIssuer("javagas") // Cria o token com o emissor 'javagas'.
                .withExpiresAt(expiresIn) // Define o tempo de expiração do token.
                .withSubject(company.getId().toString()) // Define o assunto do token com o ID da empresa.
                .withClaim("roles", Arrays.asList("COMPANY")) // Adiciona uma reivindicação com os papéis da empresa.
                .sign(algorithm); // Assina o token com a chave secreta.

        // Cria o objeto DTO de resposta com o token e o tempo de expiração.
        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
           .access_token(token)
           .expires_in(expiresIn.toEpochMilli())
           .build();    
        
        return authCompanyResponseDTO; // Retorna o DTO de resposta com as informações do token.
    }
}

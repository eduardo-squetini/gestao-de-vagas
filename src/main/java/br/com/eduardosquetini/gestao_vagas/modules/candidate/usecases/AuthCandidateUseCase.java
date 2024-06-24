package br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases;

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

import br.com.eduardosquetini.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

 // Anotação que marca a classe como um serviço Spring, que pode ser injetado em outros componentes.
@Service
public class AuthCandidateUseCase {

        // Injeta o valor da propriedade 'security.tokenSecretCandidate' do arquivo de
        // configuração para a variável 'secretKeyCandidate'.
        @Value("${security.tokenSecretCandidate}")
        private String secretKeyCandidate;

        // Injeta uma instância de CandidateRepository para interagir com o banco de
        // dados de candidatos.
        @Autowired
        private CandidateRepository candidateRepository;

        // Injeta um codificador de senha para criptografar e verificar senhas.
        @Autowired
        private PasswordEncoder passwordEncoder;

        // Método que autentica um candidato e gera um token JWT se a autenticação for
        // bem-sucedida.
        public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
                        throws AuthenticationException {
                // Busca o candidato pelo nome de usuário e lança uma exceção se não encontrado.
                var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                                .orElseThrow(() -> {
                                        throw new UsernameNotFoundException("Username/password incorrect");
                                });

                // Verifica se a senha fornecida corresponde à senha armazenada para o
                // candidato.
                var passwordMatches = this.passwordEncoder
                                .matches(authCandidateRequestDTO.password(), candidate.getPassword());

                // Se a senha não corresponder, lança uma exceção de autenticação.
                if (!passwordMatches) {
                        throw new AuthenticationException();
                }

                // Configura o algoritmo de assinatura do token JWT usando a chave secreta do
                // candidato.
                Algorithm algorithm = Algorithm.HMAC256(secretKeyCandidate);
                // Define o tempo de expiração do token para 2 horas a partir do momento atual.
                var expires_in = Instant.now().plus(Duration.ofHours(2));
                // Cria o token JWT com as reivindicações necessárias e assina com o algoritmo
                // definido.
                var token = JWT.create()
                                .withIssuer("javagas")
                                .withSubject(candidate.getId().toString())
                                .withClaim("roles", Arrays.asList("CANDIDATE"))
                                .withExpiresAt(expires_in)
                                .sign(algorithm);

                // Constrói o objeto de resposta contendo o token e o tempo de expiração em milissegundos.
                var authCandidateResponse = AuthCandidateResponseDTO.builder()
                                .access_token(token)
                                .expires_in(expires_in.toEpochMilli())
                                .build();

                // Retorna o objeto de resposta com o token e informações de expiração.
                return authCandidateResponse;

        }

}
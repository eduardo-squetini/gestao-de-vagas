package br.com.eduardosquetini.gestao_vagas.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    
    // Método que converte um objeto em uma string JSON.
    public static String objectToJson(Object obj){
        try {
            final ObjectMapper objectMapper = new ObjectMapper(); // Cria uma instância do ObjectMapper para mapeamento de objetos.
            return objectMapper.writeValueAsString(obj); // Converte o objeto para uma string JSON.
        } catch (Exception e) {
            throw new RuntimeException(e); // Lança uma exceção em tempo de execução se ocorrer um erro durante a conversão.
        }
    }

    // Método que gera um token JWT para autenticação.
    public static String generateToken(UUID idCompany, String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret); // Define o algoritmo de assinatura do token.

        var expiresIn = Instant.now().plus(Duration.ofHours(2)); // Define o tempo de expiração do token para 2 horas a partir de agora.

        var token = JWT.create().withIssuer("javagas") // Cria um novo token com o emissor "javagas".
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2))) // Define o tempo de expiração do token.
                .withSubject(idCompany.toString()) // Define o assunto do token com o ID da empresa.
                .withExpiresAt(expiresIn) // Define novamente o tempo de expiração do token (redundante, pode ser removido).
                .withClaim("roles", Arrays.asList("COMPANY")) // Adiciona uma reivindicação com os papéis do usuário.
                .sign(algorithm); // Assina o token com o algoritmo definido.

        return token; // Retorna o token gerado.
    }
}
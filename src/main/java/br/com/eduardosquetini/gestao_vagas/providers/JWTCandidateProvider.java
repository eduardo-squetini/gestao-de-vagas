package br.com.eduardosquetini.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service // Anotação do Spring que marca a classe como um serviço, que é um componente especializado para realizar lógica de segurança.
public class JWTCandidateProvider {

    @Value("${security.tokenSecretCandidate}") // Anotação do Spring que injeta o valor da propriedade 'security.tokenSecretCandidate' do arquivo de configuração.
    private String secretKeyCandidate; // Chave secreta usada para verificar o token JWT.

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", ""); // Remove o prefixo 'Bearer ' do token.

        Algorithm algorithm = Algorithm.HMAC256(secretKeyCandidate); // Define o algoritmo de verificação do token.

        try {
            // Tenta verificar o token com o algoritmo definido e retorna o token decodificado se for válido.
            var tokenDecoded = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return tokenDecoded;

        } catch (JWTVerificationException e) {
            e.printStackTrace(); // Imprime a pilha de exceção se houver um erro na verificação.
            return null; // Retorna nulo se o token não puder ser verificado.
        }
    }

}
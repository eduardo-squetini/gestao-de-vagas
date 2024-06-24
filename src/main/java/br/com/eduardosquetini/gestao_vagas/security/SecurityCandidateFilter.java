package br.com.eduardosquetini.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.eduardosquetini.gestao_vagas.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Anotação do Spring que marca a classe como um componente, que é um bean gerenciado pelo Spring.
public class SecurityCandidateFilter extends OncePerRequestFilter {

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private JWTCandidateProvider jwtProvider;  // Provedor JWT para validação de tokens.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

      //  SecurityContextHolder.getContext().setAuthentication(null);
      // Obtém o cabeçalho 'Authorization' da requisição.
        String header = request.getHeader("Authorization");

        if(request.getRequestURI().startsWith("/candidate")){

             // Valida o token usando o provedor JWT.
            if (header != null) {
                var token = this.jwtProvider.validateToken(header);
    
                // Define o status da resposta como não autorizado se o token for inválido.
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                // Define o atributo 'candidate_id' na requisição com o assunto do token.
                request.setAttribute("candidate_id", token.getSubject());
                // Obtém as reivindicações de 'roles' do token.
                var roles = token.getClaim("roles").asList(Object.class);

                //"ROLE_"

                var grants = roles.stream().map(
                    // Mapeia as reivindicações de 'roles' para autoridades concedidas.
                    role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())
                ).toList();

                // Cria um novo token de autenticação com as autoridades concedidas.
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null,
                        grants);

                 // Define a autenticação no contexto de segurança.
                SecurityContextHolder.getContext().setAuthentication(auth);

            }

        }

       

        filterChain.doFilter(request, response);
    }

}

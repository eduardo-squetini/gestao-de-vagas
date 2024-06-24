package br.com.eduardosquetini.gestao_vagas.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.eduardosquetini.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Anotação do Spring que marca a classe como um componente, que é um bean gerenciado pelo Spring.
@Component
public class SecurityCompanyFilter extends OncePerRequestFilter {

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private JWTProvider jwtProvider;  // Provedor JWT para validação de tokens.

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        //SecurityContextHolder.getContext().setAuthentication(null);
        // Obtém o cabeçalho 'Authorization' da requisição
        String header = request.getHeader("Authorization"); 
       

        // Verifica se a URI da requisição começa com '/company'.
        if (request.getRequestURI().startsWith("/company")) {

            // Valida o token usando o provedor JWT.
            if (header != null) {
                var token = this.jwtProvider.validateToken(header);

                 // Define o status da resposta como não autorizado se o token for inválido.
                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                // Obtém as reivindicações de 'roles' do token.
                var roles = token.getClaim("roles").asList(Object.class);
                var grants = roles.stream()
                 // Mapeia as reivindicações de 'roles' para autoridades concedidas.
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()) )
                .toList();

                 // Define o atributo 'company_id' na requisição com o assunto do token.
                request.setAttribute("company_id", token.getSubject());
                // Cria um novo token de autenticação com as autoridades concedidas.
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
                         null,
                        grants);
                // Define a autenticação no contexto de segurança.
                SecurityContextHolder.getContext().setAuthentication(auth);

            }

        }
        filterChain.doFilter(request, response);
    }

}

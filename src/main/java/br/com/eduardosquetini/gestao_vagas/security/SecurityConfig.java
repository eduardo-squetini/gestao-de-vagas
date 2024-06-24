package br.com.eduardosquetini.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration // Anotação do Spring que marca a classe como uma fonte de definições de bean.
@EnableMethodSecurity // Anotação do Spring Security que permite a segurança em nível de método.
public class SecurityConfig {

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private SecurityCompanyFilter securityCompanyFilter; // Filtro de segurança para empresas.

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private SecurityCandidateFilter securityCandidateFilter; // Filtro de segurança para candidatos.

    private static final String[] SWAGGER_LIST = { // Lista de caminhos para a documentação do Swagger.
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "swagger-resources/**"
    };

    /* Desabilitando configurações iniciais do Spring Security */
    @Bean // Anotação do Spring que marca o método como um produtor de bean.
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Desabilita a proteção CSRF.
                .authorizeHttpRequests(auth -> {
                    /* autorizando rotas */
                    auth.requestMatchers("/candidate/").permitAll() // Permite acesso sem autenticação para rotas específicas.
                            .requestMatchers("/company/").permitAll()
                            .requestMatchers("/company/auth").permitAll()
                            .requestMatchers("/candidate/auth").permitAll()
                            .requestMatchers(SWAGGER_LIST).permitAll();
                          

                    /* demais rotas com autenticação */
                    auth.anyRequest().authenticated(); // Requer autenticação para todas as outras rotas.
                })
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class) // Adiciona o filtro de segurança para candidatos antes do filtro básico de autenticação.
                .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class) // Adiciona o filtro de segurança para empresas antes do filtro básico de autenticação.
            ;

        ;
        return http.build(); // Constrói a cadeia de filtros de segurança.
    }

    @Bean // Anotação do Spring que marca o método como um produtor de bean.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Retorna um codificador de senha que usa BCrypt.
    }

}
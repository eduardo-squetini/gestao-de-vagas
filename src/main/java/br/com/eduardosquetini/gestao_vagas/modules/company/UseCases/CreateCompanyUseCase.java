package br.com.eduardosquetini.gestao_vagas.modules.company.UseCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.eduardosquetini.gestao_vagas.exceptions.UserFoundException;
import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.CompanyEntity;
import br.com.eduardosquetini.gestao_vagas.modules.company.repositories.CompanyRepository;

import org.springframework.beans.factory.annotation.Value;

@Service // Anotação do Spring que marca a classe como um serviço, que é um componente especializado para realizar lógica de negócios.
public class CreateCompanyUseCase {

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private CompanyRepository companyRepository; // Repositório para operações de banco de dados relacionadas a empresas.

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private PasswordEncoder passwordEncoder; // Codificador de senha para criptografar senhas.

    @Value("url-login-api") // Anotação do Spring que injeta o valor da propriedade 'url-login-api' do arquivo de configuração.
    private String urlLoginApi; // URL da API de login.

    @Value("email-login-api") // Anotação do Spring que injeta o valor da propriedade 'email-login-api' do arquivo de configuração.
    private String emailLoginApi; // Email usado para login na API.

    @Value("password-login-api") // Anotação do Spring que injeta o valor da propriedade 'password-login-api' do arquivo de configuração.
    private String passwordLoginApi; // Senha usada para login na API.

    /* Verificação de existência de usuário */
    public CompanyEntity execute(CompanyEntity companyEntity) {
        Optional<CompanyEntity> existingUser = this.companyRepository
                .findByUsername(companyEntity.getUsername()); // Verifica se já existe um usuário com o mesmo nome de usuário.
        Optional<CompanyEntity> existingEmail = this.companyRepository
                .findByEmail(companyEntity.getEmail()); // Verifica se já existe um usuário com o mesmo email.
        if (existingUser.isPresent() || existingEmail.isPresent()) {
            throw new UserFoundException(); // Lança uma exceção se o usuário ou email já existir.
        }

        /* Criptografando senha da empresa */
        var password = passwordEncoder.encode(companyEntity.getPassword()); // Criptografa a senha da empresa.
        companyEntity.setPassword(password); // Define a senha criptografada na entidade da empresa.

        return this.companyRepository.save(companyEntity); // Salva a entidade da empresa no banco de dados e retorna-a.
    }

}

package br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.eduardosquetini.gestao_vagas.exceptions.UserFoundException;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.entity.CandidateEntity;

// Anotação que marca a classe como um serviço Spring, que pode ser injetado em outros componentes.
@Service 
public class CreateCandidateUseCase {

    // Injeta uma instância de CandidateRepository para interagir com o banco de dados de candidatos.
    @Autowired
    private CandidateRepository candidateRepository;

    // Injeta um codificador de senha para criptografar senhas.
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método que cria um novo candidato no banco de dados se o nome de usuário e e-mail não existirem.
    public CandidateEntity execute(CandidateEntity candidateEntity) {
        // Verifica se já existe um usuário com o mesmo nome de usuário.
        Optional<CandidateEntity> existingUser = candidateRepository.findByUsername(candidateEntity.getUsername());
        // Verifica se já existe um usuário com o mesmo e-mail.
        Optional<CandidateEntity> existingEmail = candidateRepository.findByEmail(candidateEntity.getEmail());
        // Se um nome de usuário ou e-mail existente for encontrado, lança uma exceção personalizada.
        if (existingUser.isPresent() || existingEmail.isPresent()) {
            throw new UserFoundException();
        }

        // Criptografa a senha do candidato antes de salvar no banco de dados.
        String password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        // Salva o candidato no banco de dados e retorna a entidade salva.
        return candidateRepository.save(candidateEntity);
    }
}
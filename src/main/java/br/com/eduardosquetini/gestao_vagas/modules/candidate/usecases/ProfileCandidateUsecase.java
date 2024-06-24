package br.com.eduardosquetini.gestao_vagas.modules.candidate.usecases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardosquetini.gestao_vagas.exceptions.UserNotfoundException;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.eduardosquetini.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service // Anotação que marca a classe como um serviço Spring, que pode ser injetado em outros componentes.
public class ProfileCandidateUsecase {

    // Injeta uma instância de CandidateRepository para interagir com o banco de dados de candidatos.
    @Autowired
    private CandidateRepository candidateRepository;

    // Método que recupera o perfil de um candidato pelo seu ID e retorna um DTO com as informações do perfil.
    public ProfileCandidateResponseDTO execute(UUID idCandidate){
        // Busca o candidato pelo ID fornecido e lança uma exceção se não encontrado.
        var candidate = this.candidateRepository.findById(idCandidate)
            .orElseThrow(() -> {
                throw new UserNotfoundException();
            });

        // Constrói um DTO com as informações do perfil do candidato.
        var candidateDTO = ProfileCandidateResponseDTO.builder()
            .description(candidate.getDescription()) // Descrição do candidato.
            .username(candidate.getUsername()) // Nome de usuário do candidato.
            .email(candidate.getEmail()) // E-mail do candidato.
            .name(candidate.getName()) // Nome do candidato.
            .id(candidate.getId()) // ID do candidato.
            .build();

        // Retorna o DTO contendo as informações do perfil do candidato.
        return candidateDTO;
    }
    
}
package br.com.eduardosquetini.gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//Criando construtor com password e username
@AllArgsConstructor
public class AuthCompanyDTO {
    
    /*Acesso de dados para autorização */
    private String password;
    private String username;
}

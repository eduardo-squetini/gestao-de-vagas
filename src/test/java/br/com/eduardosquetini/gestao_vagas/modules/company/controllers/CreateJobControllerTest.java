package br.com.eduardosquetini.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.eduardosquetini.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.eduardosquetini.gestao_vagas.modules.company.entitys.CompanyEntity;
import br.com.eduardosquetini.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.eduardosquetini.gestao_vagas.utils.TestUtils;


@RunWith(SpringRunner.class) // Anotação do JUnit 4 que indica como executar os testes; SpringRunner é o novo nome para SpringJUnit4ClassRunner.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // Anotação do Spring Boot Test que configura o ambiente de teste com um servidor web em uma porta aleatória.
@ActiveProfiles("test") // Anotação do Spring que define qual perfil de configuração ativar durante os testes.
public class CreateJobControllerTest {

    private MockMvc mvc; // Classe principal do Spring MVC Test Framework que permite realizar solicitações HTTP simuladas.

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private WebApplicationContext context; // Contexto da aplicação web do Spring.

    @Autowired // Anotação do Spring que marca a injeção automática de dependência.
    private CompanyRepository companyRepository; // Repositório para operações CRUD relacionadas à entidade Company.

    @Before // Anotação do JUnit 4 que indica que o método anotado deve ser executado antes de cada teste.
    public void setup(){
        mvc = MockMvcBuilders
        .webAppContextSetup(context) // Configura o contexto da aplicação web para os testes.
        .apply(SecurityMockMvcConfigurers.springSecurity()) // Aplica as configurações de segurança do Spring MVC Test.
        .build(); // Constrói o MockMvc.
    }
    

    @Test // Anotação do JUnit que marca o método como um teste.
    public void should_be_able_to_create_a_new_job() throws Exception{
        var company = CompanyEntity.builder() // Usa o padrão Builder para criar uma nova entidade Company.
        .description("COMPANY_DESCRIPTION")
        .email("email@company.com")
        .password("1234567890")
        .username("COMPANY_USERNAME")
        .name("COMPANY_NAME").build();

        company = companyRepository.saveAndFlush(company); // Salva e imediatamente sincroniza a entidade Company no banco de dados.

        var createJobDTO = CreateJobDTO.builder() // Usa o padrão Builder para criar um novo DTO para criação de vaga.
        .benefits("BENEFITS_TEST")
        .description("DESCRIPTION_TEST")
        .level("LEVEL_TEST")
        .build();

       var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/") // Realiza uma solicitação POST simulada para a rota de criação de vaga.
        .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo da solicitação como JSON.
        .content(TestUtils.objectToJson(createJobDTO)) // Converte o DTO em JSON e define como conteúdo da solicitação.
        .header("Authorization", TestUtils.generateToken(company.getId(), "JAVAGAS_@123#")) // Define o cabeçalho de autorização com um token gerado para teste.
        )
        .andExpect(MockMvcResultMatchers.status().isOk()); // Espera que o resultado seja um status HTTP OK (200).

        System.out.println(result); // Imprime o resultado no console (geralmente não é necessário em testes automatizados).
    }

    @Test // Anotação do JUnit que marca o método como um teste.
    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception{
        var createJobDTO = CreateJobDTO.builder() // Usa o padrão Builder para criar um novo DTO para criação de vaga.
        .benefits("BENEFITS_TEST")
        .description("DESCRIPTION_TEST")
        .level("LEVEL_TEST")
        .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/") // Realiza uma solicitação POST simulada para a rota de criação de vaga.
        .contentType(MediaType.APPLICATION_JSON) // Define o tipo de conteúdo da solicitação como JSON.
        .content(TestUtils.objectToJson(createJobDTO)) // Converte o DTO em JSON e define como conteúdo da solicitação.
        .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#")) // Define o cabeçalho de autorização com um token gerado com um UUID aleatório para simular uma empresa não encontrada.
        ).andExpect(MockMvcResultMatchers.status().isBadRequest()); // Espera que o resultado seja um status HTTP Bad Request (400).
    }
}
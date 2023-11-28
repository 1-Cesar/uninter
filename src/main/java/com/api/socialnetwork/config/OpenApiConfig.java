package com.api.socialnetwork.config;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI documentacao() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Social Network")
                        .description("Atividade Extensionista !")
                        .version("v1")
                        .license(new License()
                                .name("Uninter")
                                .url("https://github.com"))
                        .contact(new Contact()
                                .name("Cesar Augusto")
                                .url("https://github.com.br/1-Cesar")))
                .externalDocs(new ExternalDocumentation()
                        .description("Clique aqui para ver o diagrama de Entidade Relacionamento")
                        .url("#"));
    }

    @Bean
    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses apiResponses = operation.getResponses();

                apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
                apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
                apiResponses.addApiResponse("204", createApiResponse("Objeto Excluido!"));
                apiResponses.addApiResponse("400", createApiResponse("Erro na requisição!"));
                apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado!"));
                apiResponses.addApiResponse("404", createApiResponse("Não encontrado!"));
                apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação!"));
            }));
        };
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}

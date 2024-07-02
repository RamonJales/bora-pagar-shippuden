package com.borathings.borapagar.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openAPI() {

        OpenAPI openAPI = new OpenAPI();
        Info info = new Info();
        Contact contact = new Contact();

        return openAPI
            .info(
                info
                    .title("Bora Pagar")
                    .description("Ferramenta de auxílio ao perído de matrícula")
                    .contact(contact.email("boraPagar@gmail.com"))
                    .version("v1")
            ); 
    }

    @Bean
    GroupedOpenApi publicApi() {

        return GroupedOpenApi.builder()
            .group("controllers")
            .pathsToMatch("v1/**")
            .build();
    }

}

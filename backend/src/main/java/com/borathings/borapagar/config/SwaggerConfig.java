package com.borathings.borapagar.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openAPI() {

        OpenAPI openAPI = new OpenAPI();
        Info info = new Info();
        Contact contact = new Contact();

        return openAPI.info(
                info.title("Bora Pagar")
                        .description("Ferramenta de auxílio ao perído de matrícula")
                        .contact(contact.email("boraPagar@gmail.com"))
                        .version("v1"));
    }
}

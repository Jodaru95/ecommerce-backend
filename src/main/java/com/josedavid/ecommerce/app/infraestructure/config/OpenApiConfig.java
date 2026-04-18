package com.josedavid.ecommerce.app.infraestructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ecommerceApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Ecommerce API")
                .description("API REST desarrollada con Spring Boot")
                .version("1.0.0")
        );
    }
}

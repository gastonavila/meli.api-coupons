package com.meli.cupon.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI (Swagger).
 * 
 * Esta clase configura OpenAPI, que es la herramienta que genera la documentación de los 
 * endpoints de la API. Aquí se define la información básica de la API y la seguridad mediante 
 * JWT para los accesos a la API.
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Define la configuración personalizada para OpenAPI (Swagger).
     * 
     * Se establece la información de la API, como el título y la versión. También se configura 
     * la seguridad con JWT (Bearer Token) para la autorización y autenticación de los usuarios.
     * 
     * @return Una instancia de `OpenAPI` configurada con la información de la API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation")
                        .version("1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("bearerAuth")
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
package com.udea.parcial.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Inventario")
                        .version("1.0.0")
                        .description("Documentación para el Parcial de Arquitectura de Software"))
                // Esto agrega el header globalmente a la documentación
                .components(new Components()
                        .addParameters("VersionHeader", new Parameter()
                                .in("header")
                                .name("X-API-Version")
                                .schema(new io.swagger.v3.oas.models.media.StringSchema()._default("v1"))
                                .required(true)
                                .description("Header de versionamiento requerido (v1)")));
    }
}

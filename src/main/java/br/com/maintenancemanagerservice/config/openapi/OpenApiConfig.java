package br.com.maintenancemanagerservice.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI maintenanceServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Maintenance Manager Service")
                        .description(" ")
                        .version("1.0.0"));
    }
}

package com.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    String userIdHeader = "X-Custom-UserId";
    return new OpenAPI()
        .info(new Info()
            .title("Spring auding with hibernate envers")
            .description("Spring data jpa auditing with hibernate envers")
            .version("1.0.0"))
        .addSecurityItem(new SecurityRequirement()
            .addList(userIdHeader))
        .components(new Components()
            .addSecuritySchemes(userIdHeader,
                new SecurityScheme().name(userIdHeader)
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER))
        );
  }

}

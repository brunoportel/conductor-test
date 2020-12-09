package br.com.bbarreto.core.config;

import org.springframework.context.annotation.Configuration;

import br.com.bbarreto.core.util.CoreConstantsUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Conductor-API", version = "${spring.application.version}"))
@SecurityScheme(name = CoreConstantsUtils.SWAGGER_AUTH_TOKEN_NAME, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfig {

}

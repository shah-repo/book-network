package com.shah.book.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
//   info is not mandatory but it's good to have for documentation
info = @Info(
        contact = @Contact(
                name = "Shahfahed",
                email = "shahfahedq@gmail.com"
        ),
        description = "OpenApi documentation for spring security",
        title = "OpenApi specification - Shah",
        version = "1.0",
        license = @License(
                name = "License name",
                url = "https://some-url.com"
        ),
        termsOfService = "Terms of service"
),
        servers = {
        @Server(
                description = "Local ENV",
                url = "http://localhost:8088/api/v1"
        ),
                @Server(
                        description = "Prod ENV",
                        url = "http://prod.bookNetworkfake/api/v1"
                )
        },
        // This means for every controller in app there should be a security requirement
        security = {
        @SecurityRequirement(
                name = "bearerAuth"
        )
        }
)
@SecurityScheme(
        name = "bearerAuth", // it should be same as provided in the above @SecurityRequirement's name
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

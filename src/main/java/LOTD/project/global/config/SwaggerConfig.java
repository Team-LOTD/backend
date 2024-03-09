package LOTD.project.global.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "LOTD.project.domain";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement));
    }


    /**
     * API Swagger 등록
     */

    @Bean
    public GroupedOpenApi MemberApi() {
        final String url = "members";
        return GroupedOpenApi.builder()
                .group(url)
                .packagesToScan(BASE_PACKAGE + ".member")
                .build();
    }

    @Bean
    public GroupedOpenApi PostApi() {
        final String url = "posts";
        return GroupedOpenApi.builder()
                .group(url)
                .pathsToMatch("/" + url + "/**")
                .packagesToScan(BASE_PACKAGE + ".post")
                .build();
    }


}
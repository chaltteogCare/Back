package com.Chaltteok.DailyCheck.config;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Encoding;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";

        Components components = new Components()
                .addSecuritySchemes(jwt, new SecurityScheme()
                        .name(jwt)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(components)
                .info(apiInfo())
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList(jwt));
    }

    private Info apiInfo() {
        return new Info()
                .title("Chaltteok API") // API의 제목
                .description("찰떡케어 API") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }

//    @Bean
//    public OperationCustomizer customizeMultipart() {
//        return (operation, handlerMethod) -> {
//            if (handlerMethod.hasMethodAnnotation(PostMapping.class)) {
//                RequestBody requestBody = new RequestBody()
//                        .content(new Content()
//                                .addMediaType(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE, new MediaType()
//                                        .schema(new Schema<>().type("object")
//                                                .addProperties("file", new Schema<>().type("string").format("binary")))));
//                operation.requestBody(requestBody);
//            }
//            return operation;
//        };
//    }
}

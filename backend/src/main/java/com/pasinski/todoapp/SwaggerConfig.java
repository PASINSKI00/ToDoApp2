package com.pasinski.todoapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private List<SecurityScheme> basicScheme() {
        List<SecurityScheme> schemes = new ArrayList<>();
        schemes.add(new BasicAuth("basicAuth"));
        return schemes;
    }

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .securitySchemes(basicScheme())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pasinski.todoapp.todo")
                        .or(RequestHandlerSelectors.basePackage("com.pasinski.todoapp.registration")))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("ToDoApp2 API")
                .description("This is my second attempt at creating well documented, reusable API")
                .version("v0.1")
                .build();
    }
}

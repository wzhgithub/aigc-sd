package com.yiyun.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableOpenApi
@EnableWebMvc
public class App {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.run(args);
    }

    @Bean
    public Docket api() {
        /* https://www.51cto.com/article/714506.html */
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yiyun.ai.controller"))
                .build();
    }
}

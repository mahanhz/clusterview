package org.amhzing.clusterview.appui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class UISwaggerConfig {

    @Bean
    public Docket uiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("app-ui")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.amhzing.clusterview.appui.web.controller"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }
}

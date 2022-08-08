package org.project.generator.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author jyj
 * @Date 2020/4/23
 **/
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        ApiInfoBuilder result = new ApiInfoBuilder();
        result.title("接口文档");
        result.termsOfServiceUrl("");
        result.contact("");
        result.version("1.0");
        return (new Docket(DocumentationType.SWAGGER_2)).apiInfo(result.build()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).paths(PathSelectors.any()).build();
    }
}

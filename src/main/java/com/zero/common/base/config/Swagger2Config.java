package com.zero.common.base.config;

import com.zero.common.security.JWTConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: swagger2 配置
 */

@Configuration
@EnableSwagger2
public class Swagger2Config  {
    

    @Bean
    public Docket createRestApi() {

        List<Parameter> parameterList = new ArrayList<>();

        //初始化公共header参数
        parameterList.add(new ParameterBuilder().name(JWTConfigurer.AUTHORIZATION)
                .description("登录token").modelRef(new ModelRef("string")).parameterType("header")
                .hidden(false).required(false).defaultValue("").build());


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zero.project.web.controller"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .globalOperationParameters(parameterList);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("base project api")
                .description("")
                .termsOfServiceUrl("localhost:8001")
                .version("v1")
                .build();
    }

}
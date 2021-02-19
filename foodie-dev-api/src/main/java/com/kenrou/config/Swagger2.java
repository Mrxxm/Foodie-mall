package com.kenrou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * 访问默认ui：    http://localhost:8018/swagger-ui.html  原路径
     * bootstrap-ui：http://localhost:8018/doc.html
     */

    // 配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)      // 指定api类型为Swagger2
                    .apiInfo(apiInfo())                     // 用户定义api文档汇总信息
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.kenrou.controller")) // 指定controller包
                    .paths(PathSelectors.any())             // 所有controller
                    .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("电商平台接口api")                      // 文档页标题
                .contact(new Contact("kenrou",        // 联系人信息
                        "http://doublex-man.com",
                        "13777891945@163.com"))
                .description("api文档")                      // 详细信息
                .version("1.0.1")                           // 文档版本号
                .termsOfServiceUrl("https://kenrou.com")    // 网站地址
                .build();
    }
}

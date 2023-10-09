package com.pzy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @description:  swagger文档配置
 * @author: 庞中原
 * @date: 2022/5/23 10:04
 */

@EnableOpenApi
@Configuration
public class SwaggerConfig {

    @Value("${config.swagger3.flag}") //读取配置文件
    private boolean flag;

    @Bean
    public Docket docket(Environment environment) {
        return new Docket(DocumentationType.OAS_30) // 指定3.0版本
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pzy"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        return new ApiInfo("lp博客社区",
                "基于SpringBoot、mybatisplus、mysql、vue2.0、element ui、nuxt 博客社区系统",
                "v1.0",
                "",
                new Contact("庞中原", "http://www,laopang35.cn", "pzy1013@163.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}

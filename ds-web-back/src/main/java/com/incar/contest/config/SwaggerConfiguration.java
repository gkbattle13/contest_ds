package com.incar.contest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 *
 * @author Kong, created on 2019-05-29T11:14.
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())

                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.incar"))
                .build();
    }

    /**
     * API文档名称和版本
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("英卡竞赛数据流项目API文档")
                .description("本文档包含英卡竞赛数据流项目API数据接口。")
                .version("1.0.0-SNAPSHOT") // 版本
                .build();
    }


    /**
     * 参数配置
     * @return
     */
    private ParameterBuilder getParameterBuilder(String name, String description, String parameterType) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name(name).description(description)
                .modelRef(new ModelRef("string"))
                .parameterType(parameterType)
                .required(true);
        return parameterBuilder;
    }
}

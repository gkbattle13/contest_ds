package com.incarcloud.config;

import com.incarcloud.common.share.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
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
                .globalOperationParameters(operationParameters())

                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.incarcloud"))
                .build();
    }

    /**
     * API文档名称和版本
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("智联钥匙API文档-智联钥匙")
                .description("本文档包含智联钥匙与TSP平台API数据接口。")
                .version("1.0.0-SNAPSHOT") // 版本
                .build();
    }

    /**
     * 全局配置参数说明
     *
     * @return
     */
    private List<Parameter> operationParameters() {
        // Header 请求参数
        List<Parameter> params = new ArrayList<>();
        params.add(getParameterBuilder(Constant.HeaderParam.SMART_KEY_IMEI, "国际移动设备识别码", "header").build());
        params.add(getParameterBuilder(Constant.HeaderParam.SMART_KEY_TS, "时间戳", "header").build());
        params.add(getParameterBuilder(Constant.HeaderParam.SMART_KEY_SIGN, "签名", "header").build());
        params.add(getParameterBuilder(Constant.HeaderParam.SMART_KEY_TYPE, "操作类型", "header").build());
        params.add(getParameterBuilder(Constant.HeaderParam.SMART_KEY_CODE, "校验码", "header").build());

        // 版本控制
        ParameterBuilder versionParameterBuilder = new ParameterBuilder();
        versionParameterBuilder.name("apiVersion").description("API版本信息")
                .modelRef(new ModelRef("string"))
                .parameterType("query")
                .required(false);
        // 组装
        params.add(versionParameterBuilder.build());

        // 国际化
        ParameterBuilder i18nParameterBuilder = new ParameterBuilder();
        i18nParameterBuilder.name(LocaleChangeInterceptor.DEFAULT_PARAM_NAME).description("国际化参数，默认zh_CN(简体中文), en_US(英文)")
                .modelRef(new ModelRef("string"))
                .parameterType("query")
                .required(false);

        // 组装
        params.add(i18nParameterBuilder.build());

        return params;
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

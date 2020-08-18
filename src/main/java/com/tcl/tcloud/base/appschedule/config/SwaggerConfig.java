package com.tcl.tcloud.base.appschedule.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author xiaosheng1.li
 **/
@Configuration
@Profile({"local", "dev", "sit"})
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi DemandBaseApi(){
        return GroupedOpenApi.builder()
                .setGroup("需求基础信息接口")
                .pathsToMatch("/demand/base/**")
                .packagesToScan("com.tcl.tcloud.base.apppush.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi OperationDemandApi(){
        return GroupedOpenApi.builder()
                .setGroup("运营类需求信息接口")
                .pathsToMatch("/demand/operation/**")
                .packagesToScan("com.tcl.tcloud.base.apppush.controller")
                .build();
    }

    @Bean
    public OpenAPI openAPI(){
        Contact contact = new Contact();
        contact.setName("Shelton_Lee");
        contact.setEmail("xiaosheng1.li@tcl.com");
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearer-jwt", new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER).name("Authorization")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write")))
                .info(new Info().title("认证授权接口")
                        .version("v1").contact(contact)
                        .description("需求基础信息接口," +
                                "运营类需求信息接口"));
    }
}

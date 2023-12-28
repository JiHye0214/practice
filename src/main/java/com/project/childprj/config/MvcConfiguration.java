package com.project.childprj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("${app.upload.path}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("\tLocalMvcConfiguration.addResourceHandlers() is called");

        registry
                .addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadDir + "/")
        ;
    }
}

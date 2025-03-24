package com.example.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String uploadDir = Paths.get("uploads").toAbsolutePath().toUri().toString();
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations(uploadDir);
//    }

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/uploads/");

        // Đường dẫn riêng cho thư mục /uploads/Image/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
        registry.addResourceHandler("/Image/**")
                .addResourceLocations("file:///C:/uploads/");
        registry.addResourceHandler("/Image/**")
                .addResourceLocations("file:" + uploadPath + "/");

    }

    @PostConstruct
    public void initUploadDirs() {
        File uploadDir = new File(uploadPath);
        File tempDir = new File(uploadPath + "temp");

        if (!uploadDir.exists()) uploadDir.mkdirs();
        if (!tempDir.exists()) tempDir.mkdirs();

        System.out.println("Upload Path: " + uploadDir.getAbsolutePath());
        System.out.println("Temp Path: " + tempDir.getAbsolutePath());
    }
}

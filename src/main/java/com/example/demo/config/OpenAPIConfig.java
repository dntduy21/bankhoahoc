package com.example.demo.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    private Contact createContact() {
        return new Contact()
                .name("Nhóm 7 sáng thứ 6");
    }

    private Info createApiInfo() {
        return new Info()
                .title("Bán khoá học API")
                .version("1.0")
                .contact(createContact())
                .description("Website bán khoá học");
    }
}

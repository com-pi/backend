package com.example.imagemodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.common"
})
public class ImageModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageModuleApplication.class, args);
    }

}

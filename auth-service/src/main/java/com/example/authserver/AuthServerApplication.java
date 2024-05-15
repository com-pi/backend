package com.example.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "com.example.authserver",
        "com.example.common",
        "com.example.imagemodule"
})
@EnableFeignClients
public class AuthServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthServerApplication.class, args);
    }

}

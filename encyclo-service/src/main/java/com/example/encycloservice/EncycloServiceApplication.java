package com.example.encycloservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EncycloServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncycloServiceApplication.class, args);
	}

}

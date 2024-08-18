package com.example.boardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
		"com.example.boardservice",
		"com.example.common",
		"com.example.imagemodule"
})
@EnableFeignClients
public class BoardServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(BoardServiceApplication.class, args);
	}
}

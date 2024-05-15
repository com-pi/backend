package com.example.boardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.example.boardservice",
		"com.example.common",
		"com.example.imagemodule"
})
public class BoardServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(BoardServiceApplication.class, args);
	}
}

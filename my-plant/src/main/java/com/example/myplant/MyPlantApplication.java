package com.example.myplant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
		"com.example.myplant",
		"com.example.common",
		"com.example.imagemodule"
})
@EnableFeignClients
public class MyPlantApplication {

	public static void main(String[] args) {

		SpringApplication.run(MyPlantApplication.class, args);
	}

}

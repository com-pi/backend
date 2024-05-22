package com.example.myplant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.example.myplant",
		"com.example.common"
})
public class MyPlantApplication {

	public static void main(String[] args) {

		SpringApplication.run(MyPlantApplication.class, args);
	}

}

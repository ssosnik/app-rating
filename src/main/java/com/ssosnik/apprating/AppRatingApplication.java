package com.ssosnik.apprating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AppRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppRatingApplication.class, args);
	}

}

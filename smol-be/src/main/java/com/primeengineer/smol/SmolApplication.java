package com.primeengineer.smol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SmolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmolApplication.class, args);
	}

}

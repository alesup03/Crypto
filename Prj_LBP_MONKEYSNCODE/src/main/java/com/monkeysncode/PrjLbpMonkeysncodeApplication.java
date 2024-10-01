package com.monkeysncode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PrjLbpMonkeysncodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrjLbpMonkeysncodeApplication.class, args);
	}

}

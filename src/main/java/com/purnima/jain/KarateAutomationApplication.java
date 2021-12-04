package com.purnima.jain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KarateAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(KarateAutomationApplication.class, args);
		log.info("KarateAutomationApplication Started..........");
	}

}

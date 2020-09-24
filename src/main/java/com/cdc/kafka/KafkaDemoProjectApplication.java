package com.cdc.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class KafkaDemoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoProjectApplication.class, args);
	}

}

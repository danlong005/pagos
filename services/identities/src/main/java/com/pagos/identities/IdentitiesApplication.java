package com.pagos.identities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.pagos")
@EnableMongoRepositories
public class IdentitiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentitiesApplication.class, args);
	}

}

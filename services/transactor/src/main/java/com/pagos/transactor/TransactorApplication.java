package com.pagos.transactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.pagos")
@EnableMongoRepositories
public class TransactorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactorApplication.class, args);
	}
}

package com.pagos.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletUI {

	public static void main(String[] args) {
		SpringApplication.run(WebUi.class, args);
	}

}
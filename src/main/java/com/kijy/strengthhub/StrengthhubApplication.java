package com.kijy.strengthhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class StrengthhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrengthhubApplication.class, args);
	}
}


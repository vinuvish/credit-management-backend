package com.interview.creditmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CreditManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditManagementApplication.class, args);
	}

}

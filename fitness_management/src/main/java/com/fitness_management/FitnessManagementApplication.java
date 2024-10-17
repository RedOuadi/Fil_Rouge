package com.fitness_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "com.fitness_management")

public class FitnessManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessManagementApplication.class, args);
	}

}

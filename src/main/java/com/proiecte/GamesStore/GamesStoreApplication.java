package com.proiecte.GamesStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GamesStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamesStoreApplication.class, args);
	}

}

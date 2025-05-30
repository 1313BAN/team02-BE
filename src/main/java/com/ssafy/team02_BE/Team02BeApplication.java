package com.ssafy.team02_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Team02BeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team02BeApplication.class, args);
	}

}

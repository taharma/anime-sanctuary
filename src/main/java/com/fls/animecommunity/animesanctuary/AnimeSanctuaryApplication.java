package com.fls.animecommunity.animesanctuary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AnimeSanctuaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeSanctuaryApplication.class, args);
	}

}

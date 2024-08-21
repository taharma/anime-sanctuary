package com.fls.animecommunity.animesanctuary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Anime Sanctuary API", version = "1.0", description = "各国の主要アニメプラットフォームを一目で見ることができる「Anime Sanctuary」サービスです。"))
@EnableJpaAuditing
public class AnimeSanctuaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeSanctuaryApplication.class, args);
	}

}

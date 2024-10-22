package com.springauditappdemo;

import com.springauditappdemo.config.AuditorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "awareRef")
public class SpringAuditAppApplication {

	@Bean
	public AuditorAware<Integer> awareRef() {
		return new AuditorConfig();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringAuditAppApplication.class, args);
	}

}

package com.example.jpa_2023_c16_e1_spring_data_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Jpa2023C16E1SpringDataJpaApplication {

	public static void main(String[] args) {
//		hibernate -> entity manager, context, transaction
//		business layer -> spring data jpa -> hibernate -> jdbc
		SpringApplication.run(Jpa2023C16E1SpringDataJpaApplication.class, args);
	}

}

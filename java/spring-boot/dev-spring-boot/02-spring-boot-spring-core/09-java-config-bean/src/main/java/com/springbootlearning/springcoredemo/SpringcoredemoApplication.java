package com.springbootlearning.springcoredemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//we have moved the Coach interface and implemented class in util, so Spring will not scan it, because its outside the package, so we are explicitely defining it - below
/*@SpringBootApplication(
		scanBasePackages = {"com.springbootlearning.springcoredemo", "com.springbootlearning.util"}
)
 */

@SpringBootApplication
public class SpringcoredemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcoredemoApplication.class, args);
	}

}

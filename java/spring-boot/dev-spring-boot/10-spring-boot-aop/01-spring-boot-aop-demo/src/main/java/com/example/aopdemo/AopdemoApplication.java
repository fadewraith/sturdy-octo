package com.example.aopdemo;

import com.example.aopdemo.dao.AccountDAO;
import com.example.aopdemo.dao.MembershipDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AccountDAO accountDAO, MembershipDAO membershipDAO) {
		return runner -> {
//			demoTheBeforeAdvice(accountDAO);
			demoTheBeforeAdvice(accountDAO, membershipDAO);
		};
	}

	private void demoTheBeforeAdvice(AccountDAO accountDAO, MembershipDAO membershipDAO) {
//		call the business method
//		accountDAO.addAccount();

		Account account = new Account();
//		accountDAO.addAccount(account);
		accountDAO.addAccount(account, true);
		accountDAO.doWork();

//		membershipDAO.addAccount();
		membershipDAO.addDummyMember();
		membershipDAO.goToSleep();

//		System.out.println("\n call it again!\n");
//
//		accountDAO.addAccount();


	}

}

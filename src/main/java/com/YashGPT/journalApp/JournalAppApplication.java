package com.YashGPT.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
// import org.springframework.data.mongodb.MongoDatabaseFactory;
// import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalAppApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(JournalAppApplication.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();
		System.out.println(environment.getActiveProfiles()[0]);
	}

	// Configure a MongoTransactionManager bean to manage transactions for MongoDB operations. This allows for consistent and reliable data handling in the application.
	// @Bean
	// public PlatformTransactionManager add(MongoDatabaseFactory dbFactory) {
	// 	return new org.springframework.data.mongodb.MongoTransactionManager(dbFactory);
	// }
}

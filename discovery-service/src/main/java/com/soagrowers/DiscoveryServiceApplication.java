package com.soagrowers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * is an Spring Boot application that enables Eureka server functionality and provides
 * the main entry point for the application.
 */
@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServiceApplication {

	/**
	 * starts the discovery service application by running the `SpringApplication.run()`
	 * method with the `DiscoveryServiceApplication` class as its argument and the `args`
	 * array as its parameter.
	 * 
	 * @param args 1 or more command line arguments passed to the `SpringApplication.run()`
	 * method when invoking the `main()` function.
	 * 
	 * 	- It is an array of strings representing command-line arguments passed to the application.
	 * 	- The length of the array `args` can vary depending on the number and types of
	 * arguments provided by the user.
	 * 	- Each element in the array can hold a different value, such as a command-line
	 * option or parameter.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}
}

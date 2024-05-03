package com.soagrowers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * is marked with several annotations: `@EnableEurekaServer`, `@SpringBootApplication`,
 * and `public static void main(String[] args)`. These annotations indicate that the
 * class is a Spring Boot application that enables Eureka server functionality and
 * serves as the primary entry point for the application.
 */
@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServiceApplication {

	/**
	 * runs a SpringApplication instance for the `DiscoveryServiceApplication` class,
	 * passing the `args` array as an argument.
	 * 
	 * @param args 0 or more command-line arguments passed to the `SpringApplication.run()`
	 * method when invoking the `main()` function.
	 * 
	 * 	- The type of the array is String[].
	 * 	- The length of the array is provided by the variable `args'.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}
}

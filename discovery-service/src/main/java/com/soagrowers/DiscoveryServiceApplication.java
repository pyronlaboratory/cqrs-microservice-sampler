package com.soagrowers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServiceApplication {

	/**
	 * Runs the `DiscoveryServiceApplication` and starts its execution.
	 * 
	 * @param args 1 or more command-line arguments passed to the `SpringApplication.run()`
	 * method when executing the `DiscoveryServiceApplication`.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}
}

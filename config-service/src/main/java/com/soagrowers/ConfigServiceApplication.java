package com.soagrowers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * is a Spring Boot application that enables config server functionality and provides
 * a main method for launching the application.
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServiceApplication {

    /**
     * runs a Spring Application, passing the `ConfigServiceApplication` class as an
     * argument to the `SpringApplication.run()` method, which configures and starts the
     * application.
     * 
     * @param args 1 or more command-line arguments passed to the `SpringApplication.run()`
     * method when invoking the application.
     * 
     * 	- The function takes an array of strings called `args` as input.
     * 	- Depending on the context, this array may contain any combination of command-line
     * arguments passed to the program when it was run.
     * 	- Each string in the `args` array represents a separate argument provided by the
     * user at runtime.
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }
}

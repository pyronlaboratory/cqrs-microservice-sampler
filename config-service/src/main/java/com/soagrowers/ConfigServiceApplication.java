package com.soagrowers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServiceApplication {

    /**
     * Starts the configuration service application by running it using the
     * `SpringApplication.run()` method, passing the class and command-line arguments as
     * arguments.
     * 
     * @param args command-line arguments passed to the SpringApplication when it is run.
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }
}

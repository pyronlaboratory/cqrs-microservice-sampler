package com.soagrowers.productquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * is a Spring Boot application that enables Eureka client functionality and scans
 * the domain package for entity configuration. It also includes a REST controller
 * for retrieving service instances by application name and a message REST controller
 * for retrieving a fixed message.
 */

@EnableEurekaClient
@SpringBootApplication
@EntityScan("com.soagrowers.productquery.domain")
public class Application {

    /**
     * starts a Spring application by running the `Application` class using `SpringApplication.run`.
     */
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}

/**
 * is a RESTful controller that retrieves a list of ServiceInstances associated with
 * a specific application name using the DiscoveryClient. The controller method
 * serviceInstancesByApplicationName() takes no arguments and returns a list of ServiceInstances.
 */
@RestController
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String appName;

    /**
     * retrieves a list of service instances associated with a specified application name
     * using the Discovery Client API.
     * 
     * @returns a list of ServiceInstances associated with the specified application name.
     * 
     * 	- List<ServiceInstance>: The function returns a list of service instances for a
     * given application name.
     * 	- ServiceInstance: Each element in the list represents a service instance, which
     * contains information about the instance, such as its ID, hostname, and status.
     */
    @RequestMapping("/instances")
    public List<ServiceInstance> serviceInstancesByApplicationName() {
        return this.discoveryClient.getInstances(appName);
    }
}

/**
 * is a RESTful controller that provides a `getMessage()` function that retrieves a
 * predefined message based on a Spring Boot application's configuration file.
 */
@RefreshScope
@RestController
class MessageRestController {

    @Value("${message}")
    private String message;

    /**
     * retrieves and returns a predefined string message.
     * 
     * @returns a string containing the message "Hello, World!".
     */
    @RequestMapping("/message")
    String getMessage() {
        return this.message;
    }
}
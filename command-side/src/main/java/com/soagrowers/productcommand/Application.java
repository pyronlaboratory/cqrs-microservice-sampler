package com.soagrowers.productcommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * is a Spring Boot application that enables Eureka client functionality and provides
 * a REST controller for service instances. The main method starts the application
 * context using SpringApplication.
 */
@EnableEurekaClient
@SpringBootApplication
public class Application {

    /**
     * runs a SpringApplication and starts the application.
     */
    public static void main(String... args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
    }
}

/**
 * is a RESTful controller that provides access to service instances by application
 * name. It uses the DiscoveryClient to retrieve a list of service instances associated
 * with a given application name, and returns them in a List format.
 */
@RestController
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String appName;

    /**
     * retrieves a list of service instances associated with a given application name
     * using the Discovery API provided by the `discoveryClient`.
     * 
     * @returns a list of Service Instances associated with the specified Application Name.
     * 
     * The function returns a list of ServiceInstance objects representing the instances
     * of services associated with the given application name. Each instance is described
     * by its ID, display name, and health status. The list provides information about
     * the number of instances available and their current state.
     */
    @RequestMapping("/instances")
    public List<ServiceInstance> serviceInstancesByApplicationName() {
        return this.discoveryClient.getInstances(appName);
    }
}


/**
 * is a Spring Boot REST controller that provides a String message through a GET
 * request. The message is stored in a Spring property file and can be retrieved by
 * sending a request to the `/message` endpoint.
 */
@RefreshScope
@RestController
class MessageRestController {

    @Value("${message}")
    private String message;

    /**
     * retrieves and returns a predefined string message.
     * 
     * @returns a string containing the message value.
     */
    @RequestMapping("/message")
    String getMessage() {
        return this.message;
    }
}

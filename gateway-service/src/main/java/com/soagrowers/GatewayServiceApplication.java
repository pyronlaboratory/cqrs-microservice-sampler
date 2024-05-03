package com.soagrowers;

import com.soagrowers.prefilters.SimpleLoggingPreFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * is a Spring Boot application that enables Zuul proxy functionality and defines a
 * simple logging pre-filter. The application acts as a reverse proxy, forwarding
 * requests to other services based on routes.
 */
@EnableZuulProxy //Acts as reverse proxy, forwarding requests to other services based on routes.
@SpringBootApplication
public class GatewayServiceApplication {

    /**
     * creates a `SimpleLoggingPreFilter` instance, which logs incoming requests and
     * responses for debugging purposes.
     * 
     * @returns a `SimpleLoggingPreFilter` instance.
     * 
     * The `SimpleLoggingPreFilter` class is a pre-filter that logs incoming requests and
     * responses to the application's log. The filter is created using the `@Bean`
     * annotation. It does not have any attributes or properties explicitly defined.
     */
    @Bean
    public SimpleLoggingPreFilter simplePreFilter() {
        return new SimpleLoggingPreFilter();
    }

    /**
     * runs a Spring application, specifically the `GatewayServiceApplication`, by calling
     * the `SpringApplication.run()` method and passing it the class and argument array.
     * 
     * @param args command-line arguments passed to the application when it is launched.
     * 
     * 	- `args`: an array of String objects representing command-line arguments passed
     * to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}

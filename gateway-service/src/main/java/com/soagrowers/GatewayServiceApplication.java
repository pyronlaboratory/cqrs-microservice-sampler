package com.soagrowers;

import com.soagrowers.prefilters.SimpleLoggingPreFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy //Acts as reverse proxy, forwarding requests to other services based on routes.
@SpringBootApplication
public class GatewayServiceApplication {

    @Bean
    public SimpleLoggingPreFilter simplePreFilter() {
        return new SimpleLoggingPreFilter();
    }

    /**
     * Initializes and runs a Spring application instance for the `GatewayServiceApplication`.
     * 
     * @param args 1 or more command line arguments passed to the `SpringApplication.run()`
     * method when executing the `GatewayServiceApplication`.
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}

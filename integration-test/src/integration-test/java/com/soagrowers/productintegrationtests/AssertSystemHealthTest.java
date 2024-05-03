package com.soagrowers.productintegrationtests;


import com.soagrowers.utils.Statics;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;

/**
 * is a test class for verifying the health of various systems in a microservices
 * architecture. It contains several tests that verify the status of different
 * components such as the gateway, discovery, configuration, commands, and queries.
 * These tests use Selenium WebDriver to make HTTP requests to the appropriate endpoints
 * and check the response status code and body content to ensure that the system is
 * healthy.
 */
public class AssertSystemHealthTest {

    private static final Logger LOG = LoggerFactory.getLogger(AssertSystemHealthTest.class);

    private String productId = UUID.randomUUID().toString();


    /**
     * prints a message to the console indicating whether the system is in production
     * mode based on the value of `Statics.PRODUCTION`.
     */
    @Before
    public void setup(){
        System.out.println("PRODUCTION MODE: " + Statics.PRODUCTION);
    }

    /**
     * verifies that the gateway is healthy by checking the response status code and body
     * content of two endpoints: `/health/` and `/routes/`.
     */
    @Test
    public void assertGatewayHealth() {
        given().
                port(Statics.PORT_FOR_GATEWAY).
                when().
                get("/health/").
                then().
                statusCode(HttpStatus.SC_OK).
                body("status", Matchers.is("UP")).
                body("hystrix.status", Matchers.is("UP"));

        given().
                port(Statics.PORT_FOR_GATEWAY).
                when().
                get("/routes/").
                then().
                statusCode(HttpStatus.SC_OK);
    }

    /**
     * verifies that a discovery endpoint returns a status code of 200 OK, with specific
     * body fields matching expected values related to the Hystrix and Discovery composites.
     */
    @Test
    public void assertDiscoveryHealth() {
        given().
                port(Statics.PORT_FOR_DISCOVERY).
                when().
                get("/health/").
                then().
                statusCode(HttpStatus.SC_OK).
                body("status", Matchers.is("UP")).
                body("discoveryComposite.status", Matchers.is("UP")).
                body("hystrix.status", Matchers.is("UP"));
    }

    /**
     * verifies that the configuration server responds with an HTTP 200 status code and
     * a body containing "UP" for both the configuration server and the integration test
     * resource.
     */
    @Test
    public void assertConfigHealth() {
        given().
                port(Statics.PORT_FOR_CONFIG).
                when().
                get("/health/").
                then().
                statusCode(HttpStatus.SC_OK).
                body("status", Matchers.is("UP")).
                body("configServer.status", Matchers.is("UP"));

        given().
                port(Statics.PORT_FOR_CONFIG).
                when().
                get("/integration-test/default/master").
                then().
                statusCode(HttpStatus.SC_OK).
                body("name", Matchers.is("integration-test"));
    }

    /**
     * verifies that the gateway's health check response is UP, and the message returned
     * by the command configuration endpoint matches the expected value. It also checks
     * that the instances of the command service are running and have the expected action
     * type.
     */
    @Test
    public void assertCommandSideHealth() {

        String cmdConfigMessage;

        if(!Statics.PRODUCTION){
            cmdConfigMessage = Statics.LOCAL_CMD_MESSAGE;
        } else {
            cmdConfigMessage = Statics.PROD_CMD_MESSAGE;
        }

        given().
                port(Statics.PORT_FOR_GATEWAY).
                when().
                get("/commands/health/").
                then().
                statusCode(HttpStatus.SC_OK).
                body("status", Matchers.is("UP")).
                body("rabbit.status", Matchers.is("UP")).
                body("mongo.status", Matchers.is("UP"));

        given().
                port(Statics.PORT_FOR_GATEWAY).
                when().
                get("/commands/message").
                then().
                statusCode(HttpStatus.SC_OK).
                body(Matchers.is(cmdConfigMessage));

        given().
                port(Statics.PORT_FOR_GATEWAY).
                when().
                get("/commands/instances").
                then().
                statusCode(HttpStatus.SC_OK).
                body("serviceId", Matchers.hasItems(Statics.CMD_SERVICE_ID)).
                body("instanceInfo.actionType", Matchers.hasItems("ADDED"));
    }

    /**
     * verifies that the query service is up and running, checks the message config, and
     * retrieves instance information to ensure that the query service is properly initialized.
     */
    @Test
    public void assertQuerySideHealth() {

        String qryConfigMessage;
        if(!Statics.PRODUCTION){
            qryConfigMessage = Statics.LOCAL_QRY_MESSAGE;
        } else {
            qryConfigMessage = Statics.PROD_QRY_MESSAGE;
        }

        given().
                port(Statics.PORT_FOR_GATEWAY).
                when().
                get("/queries/health/").
                then().
                statusCode(HttpStatus.SC_OK).
                body("status", Matchers.is("UP")).
                body("db.status", Matchers.is("UP")).
                body("rabbit.status", Matchers.is("UP")).
                body("db.database", Matchers.is("H2"));

        given().
                port(Statics.PORT_FOR_GATEWAY).
                when().
                get("/queries/message").
                then().
                statusCode(HttpStatus.SC_OK).
                body(Matchers.is(qryConfigMessage));

        given().
                port(Statics.PORT_FOR_GATEWAY).
                when().
                get("/queries/instances").
                then().
                statusCode(HttpStatus.SC_OK).
                body("serviceId", Matchers.hasItems(Statics.QRY_SERVICE_ID)).
                body("instanceInfo.actionType", Matchers.hasItems("ADDED"));

    }
}

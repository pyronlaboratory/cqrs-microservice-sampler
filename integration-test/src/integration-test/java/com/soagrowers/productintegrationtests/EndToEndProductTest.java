package com.soagrowers.productintegrationtests;



import com.soagrowers.utils.Statics;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.given;
import static com.soagrowers.utils.Statics.*;

/**
 * is a JUnit test class that tests the end-to-end integration of a product creation
 * and retrieval functionality through REST API calls. The class sets up a mock
 * command-side environment, creates a new product, and then checks that the new
 * product has arrived on the query-side and been made available for clients to view.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EndToEndProductTest {

    private static final Logger LOG = LoggerFactory.getLogger(EndToEndProductTest.class);
    private static String id;
    private static String name;

    /**
     * generates a unique identifier and assigns it to a variable named `id`. It also
     * creates a string variable named `name` that combines the identifier with additional
     * text.
     */
    @BeforeClass
    public static void setupClass(){
        id = UUID.randomUUID().toString();
        name = "End2End Test Product ["+id+"]";
    }

    /**
     * pauses for 2 seconds before executing the next test in a test suite.
     */
    @After
    public void afterEach() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2l);
    }

    /**
     * posts a product to the gateway with an ID and name, returning a HTTP Status Code
     * 201 Created if successful.
     */
    @Test
    public void testA_PostAProduct() {

        given().
                port(PORT_FOR_GATEWAY).
        when().
                post(PRODUCTS_CMD_BASE_PATH + CMD_PRODUCT_ADD + "/{id}?name={name}", id, name).
        then().
                statusCode(HttpStatus.SC_CREATED);

    }
    /**
     * tests the `get()` method on a product resource, given its ID, and asserts that the
     * response status code is `HttpStatus.SC_OK` and the name of the product in the body
     * of the response matches a given value.
     */

    @Test
    public void testB_GetAProduct(){

        given().
                port(Statics.PORT_FOR_GATEWAY).
        when().
                get(PRODUCTS_QRY_BASE_PATH + "/{id}", id).
        then().
                statusCode(HttpStatus.SC_OK).
                body("name", Matchers.is(name));
    }
}

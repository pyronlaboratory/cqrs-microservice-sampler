package com.soagrowers.productintegrationtests;

import org.apache.http.HttpStatus;
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
 * is a JUnit test class that tests the addition of duplicate products through the
 * API. The class has two tests, one that successfully adds a product with a unique
 * ID and name, and another that attempts to add a duplicate product and receives a
 * conflict status code.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddDuplicateProductTest {

    private static final Logger LOG = LoggerFactory.getLogger(AddDuplicateProductTest.class);
    private static String id;
    private static String name;

    /**
     * generates a unique identifier and sets a product name based on the identifier,
     * both initialized as strings.
     */
    @BeforeClass
    public static void setupClass() {
        id = UUID.randomUUID().toString();
        name = "Duplicate Testing Product [" + id + "]";
    }

    /**
     * delays the execution of the subsequent test by 2 seconds using `TimeUnit.SECONDS.sleep()`
     * method.
     */
    @After
    public void afterEach() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2l);
    }

    /**
     * tests whether adding a product with an existing ID fails with a `HttpStatus.SC_CONFLICT`
     * status code.
     */
    @Test
    public void testAddOfDuplicatesFailsPartA() {
        given().
                port(PORT_FOR_GATEWAY).
                when().
                post(PRODUCTS_CMD_BASE_PATH + CMD_PRODUCT_ADD + "/{id}?name={name}", id, name).
                then().
                statusCode(HttpStatus.SC_CREATED);
    }

    /**
     * tests whether adding a product with an already existing ID fails and returns a
     * conflict status code (409).
     */
    @Test
    public void testAddOfDuplicatesFailsPartB() {
        given()
                .port(PORT_FOR_GATEWAY)
                .when()
                .post(PRODUCTS_CMD_BASE_PATH + CMD_PRODUCT_ADD + "/{id}?name={name}", id, name)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT);
    }
}

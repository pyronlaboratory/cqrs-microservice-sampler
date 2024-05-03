package com.soagrowers.utils;

/**
 * provides static values and constants for various ports, service IDs, API versions,
 * and message strings used by other classes in the package.
 * Fields:
 * 	- PORT_FOR_GATEWAY (int): represents the default port number used by the gateway
 * microservice to receive incoming requests.
 * 	- PORT_FOR_COMMANDS (int): is set to 9000 in the Statics class.
 * 	- PORT_FOR_QUERIES (int): represents the port number used by the Statics class
 * for query requests.
 * 	- PORT_FOR_DISCOVERY (int): represents the port number used for the discovery
 * service in the given Java code.
 * 	- PORT_FOR_CONFIG (int): in the Statics class represents the port number used by
 * the configuration microservice.
 * 	- QRY_SERVICE_ID (String): represents a unique identifier for the product query
 * side of a microservice architecture.
 * 	- CMD_SERVICE_ID (String): represents the identifier for the command-side
 * microservice in the given Java code.
 * 	- API (String): in the Statics class defines a set of endpoints for different
 * services, including PRODUCTS_CMD_BASE_PATH and PRODUCTS_QRY_BASE_PATH for
 * product-related commands and queries, respectively.
 * 	- VERSION (String): in the Statics class represents an unknown value.
 * 	- CMD_ROUTE (String): represents the path for product commands within the Statics
 * class in Java.
 * 	- QRY_ROUTE (String): represents the route for query endpoints in the Statics class.
 * 	- PRODUCTS_CMD_BASE_PATH (String): represents the base path for accessing
 * products-related commands in the microservice.
 * 	- PRODUCTS_QRY_BASE_PATH (String): represents the base path for querying products
 * in the application.
 * 	- CMD_PRODUCT_ADD (String): represents the URL path for adding products in the
 * PRODUCTION microservice configuration.
 * 	- PROD_CMD_MESSAGE (String): contains a greeting message from the PRODUCT-COMMAND-SIDE
 * microservice using the PRODUCTION config.
 * 	- PROD_QRY_MESSAGE (String): in the Statics class is a message indicating greetings
 * from the PRODUCT-QUERY-SIDE microservice using the PRODUCTION config.
 * 	- LOCAL_CMD_MESSAGE (String): is "Greetings from the PRODUCT-COMMAND-SIDE
 * microservice [using the LOCALHOST config]."
 * 	- LOCAL_QRY_MESSAGE (String): contains a message indicating greetings from the
 * PRODUCT-QUERY-SIDE microservice using the LOCALHOST config.
 * 	- PRODUCTION (boolean): in Java's Statics class indicates whether the microservices
 * are running in production mode or not, which is determined by the value of a system
 * property set using System.getProperty("production").
 */
public class Statics {

    public static final int PORT_FOR_GATEWAY = 8080;
    public static final int PORT_FOR_COMMANDS = 9000;
    public static final int PORT_FOR_QUERIES = 9001;
    public static final int PORT_FOR_DISCOVERY = 8761;
    public static final int PORT_FOR_CONFIG = 8888;
    public static final String QRY_SERVICE_ID = "PRODUCT-QUERY-SIDE";
    public static final String CMD_SERVICE_ID = "PRODUCT-COMMAND-SIDE";


    public static final String API = "";
    public static final String VERSION = "";
    public static final String CMD_ROUTE = "/commands";
    public static final String QRY_ROUTE = "/queries";
    public static final String PRODUCTS_CMD_BASE_PATH = API + VERSION + CMD_ROUTE + "/products";
    public static final String PRODUCTS_QRY_BASE_PATH = API + VERSION + QRY_ROUTE + "/products";
    public static final String CMD_PRODUCT_ADD = "/add";

    public static final String PROD_CMD_MESSAGE = "Greetings from the PRODUCT-COMMAND-SIDE microservice [using the PRODUCTION config].";
    public static final String PROD_QRY_MESSAGE = "Greetings from the PRODUCT-QUERY-SIDE microservice [using the PRODUCTION config].";

    public static final String LOCAL_CMD_MESSAGE = "Greetings from the PRODUCT-COMMAND-SIDE microservice [using the LOCALHOST config].";
    public static final String LOCAL_QRY_MESSAGE = "Greetings from the PRODUCT-QUERY-SIDE microservice [using the LOCALHOST config].";

    public static final boolean PRODUCTION = Boolean.valueOf(System.getProperty("production", "true"));

}

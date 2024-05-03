package com.soagrowers.productcommand;

import com.soagrowers.productcommand.commands.AddProductCommand;
import com.soagrowers.utils.Asserts;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.repository.ConcurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * is responsible for handling HTTP POST requests to the /products endpoint, where
 * users can add new products. The controller uses the CommandGateway to send and
 * wait for the AddProductCommand, which adds the product to the database. If any
 * errors occur during the command execution, the controller handles them and sets
 * the appropriate response status code.
 */
@RestController
@RequestMapping("/products")
public class ProductRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductRestController.class);

    @Autowired
    CommandGateway commandGateway;

    /**
     * handles a POST request to add a product to the database, validating input parameters
     * and sending a command to the gateway to add the product. If any validation errors
     * occur or if the command execution fails, it returns an appropriate response.
     * 
     * @param id product ID which is used to identify the product being added.
     * 
     * @param name name of the product being added and is required to be provided by the
     * user for the addition to be successful.
     * 
     * @param response HTTP response object, which is used to set the status code and
     * provide additional information to the client in case of an error or success.
     * 
     * 	- `response`: A `HttpServletResponse` object that provides information about the
     * HTTP request and response. It has various properties such as status code, headers,
     * and query strings.
     * 	- `setStatus(statusCode)`: Sets the status code of the response to the specified
     * value. The status code is a three-digit code that indicates the result of the
     * request. For example, 200 means OK, while 404 means Not Found.
     * 	- `headers`: A collection of headers associated with the response. Each header
     * has a key-value pair that provides additional information about the response.
     * 	- `queryStrings`: An array of query strings associated with the response. Each
     * query string is a string that contains a set of keywords separated by the '='
     * character, and each keyword has a value associated with it.
     * 
     * In this function, the `response` object is used to set the status code and headers
     * of the response. The `queryStrings` property is not used in this specific function.
     */
    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public void add(@PathVariable(value = "id") String id,
                    @RequestParam(value = "name", required = true) String name,
                    HttpServletResponse response) {

        LOG.debug("Adding Product [{}] '{}'", id, name);

        try {
            Asserts.INSTANCE.areNotEmpty(Arrays.asList(id, name));
            AddProductCommand command = new AddProductCommand(id, name);
            commandGateway.sendAndWait(command);
            LOG.info("Added Product [{}] '{}'", id, name);
            response.setStatus(HttpServletResponse.SC_CREATED);// Set up the 201 CREATED response
            return;
        } catch (AssertionError ae) {
            LOG.warn("Add Request failed - empty params?. [{}] '{}'", id, name);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (CommandExecutionException cex) {
            LOG.warn("Add Command FAILED with Message: {}", cex.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            if (null != cex.getCause()) {
                LOG.warn("Caused by: {} {}", cex.getCause().getClass().getName(), cex.getCause().getMessage());
                if (cex.getCause() instanceof ConcurrencyException) {
                    LOG.warn("A duplicate product with the same ID [{}] already exists.", id);
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                }
            }
        }
    }
}

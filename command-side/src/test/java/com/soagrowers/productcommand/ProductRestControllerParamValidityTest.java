package com.soagrowers.productcommand;

import com.soagrowers.utils.Asserts;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * tests various scenarios for adding products to the server. It includes tests for
 * good request parameters, assertion errors, and bad request parameters. The class
 * sets up mock dependencies using Mockito Annotations and verifies the responses of
 * the CommandGateway interface using verify() method.
 */
public class ProductRestControllerParamValidityTest {

    ProductRestController controller;
    MockHttpServletResponse mockHttpServletResponse;

    @Mock
    CommandGateway gateway;

    /**
     * sets up various components and configurations for testing purposes, including
     * initializing MockitoAnnotations and setting Asserts to true, creating a new instance
     * of `ProductRestController`, and providing a mock instance of `MockHttpServletResponse`.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Asserts.INSTANCE.setAssertsTo(true);
        controller = new ProductRestController();
        mockHttpServletResponse = new MockHttpServletResponse();
    }

    /**
     * tests the `add` method of a controller by providing valid request parameters and
     * verifying that the expected response is returned.
     */
    @Test
    public void testAddWithGoodRequestParams() {
        // Arrange
        controller.commandGateway = gateway; //cheating a bit here, but mocking all the axon framework's beans is a pain.
        when(gateway.sendAndWait(any())).thenReturn(null);

        //Act
        controller.add(UUID.randomUUID().toString(), "Test Add Product", mockHttpServletResponse);

        //Assert
        verify(gateway).sendAndWait(any());
        assertTrue(mockHttpServletResponse.getStatus() == HttpServletResponse.SC_CREATED);
    }

    /**
     * tests the `add` method of a controller by mocking the gateway's `sendAndWait`
     * method to throw an `AssertionError`. The method verifies that the gateway's
     * `sendAndWait` was called with the correct arguments and asserts that the response
     * status code is `HttpServletResponse.SC_BAD_REQUEST`.
     */
    @Test
    public void testFailedAddWithAssertionError() {
        // Arrange
        controller.commandGateway = gateway; //cheating a bit here, but mocking all the axon framework's beans is a pain.
        when(gateway.sendAndWait(any())).thenThrow(AssertionError.class);

        //Act
        controller.add(UUID.randomUUID().toString(), "Test Add Product", mockHttpServletResponse);

        //Assert
        verify(gateway).sendAndWait(any());
        assertTrue(mockHttpServletResponse.getStatus() == HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * tests the Add Product endpoint of a controller by throwing a `CommandExecutionException`
     * when sending a request to the gateway.
     */
    @Test
    public void testFailedAddWithCommandExecutionException() {
        // Arrange
        controller.commandGateway = gateway; //cheating a bit here, but mocking all the axon framework's beans is a pain.
        when(gateway.sendAndWait(any())).thenThrow(CommandExecutionException.class);

        //Act
        controller.add(UUID.randomUUID().toString(), "Test Add Product", mockHttpServletResponse);

        //Assert
        verify(gateway).sendAndWait(any());
        assertTrue(mockHttpServletResponse.getStatus() == HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * tests the `add` method of a controller by providing invalid input parameters and
     * verifying the resulting HTTP status code is `SC_BAD_REQUEST`.
     */
    @Test
    public void testAddWithBadRequestParams() {

        controller.add(null, null, mockHttpServletResponse);
        assertTrue(mockHttpServletResponse.getStatus() == HttpServletResponse.SC_BAD_REQUEST);

        controller.add(UUID.randomUUID().toString(), null, new MockHttpServletResponse());
        assertTrue(mockHttpServletResponse.getStatus() == HttpServletResponse.SC_BAD_REQUEST);

        controller.add(UUID.randomUUID().toString(), "", new MockHttpServletResponse());
        assertTrue(mockHttpServletResponse.getStatus() == HttpServletResponse.SC_BAD_REQUEST);

        controller.add("", "", new MockHttpServletResponse());
        assertTrue(mockHttpServletResponse.getStatus() == HttpServletResponse.SC_BAD_REQUEST);

    }
}

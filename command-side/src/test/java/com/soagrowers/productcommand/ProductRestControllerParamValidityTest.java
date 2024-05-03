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
 * tests various scenarios for adding products to the system, including successful
 * requests with valid data and failed requests due to AssertionError or
 * CommandExecutionException. The class also verifies that bad request parameters
 * result in a BAD_REQUEST status code.
 */
public class ProductRestControllerParamValidityTest {

    ProductRestController controller;
    MockHttpServletResponse mockHttpServletResponse;

    @Mock
    CommandGateway gateway;

    /**
     * initializes various components and sets up mock objects for testing purposes,
     * including the `ProductRestController`, `MockHttpServletResponse`, and enables
     * assertions for testing.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Asserts.INSTANCE.setAssertsTo(true);
        controller = new ProductRestController();
        mockHttpServletResponse = new MockHttpServletResponse();
    }

    /**
     * tests the `add` method of a controller by providing valid parameters and verifying
     * that the expected HTTP status code is returned.
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
     * tests the controller's `add` method by mocking the gateway's `sendAndWait` method
     * to throw an AssertionError. The function verifies that the response status code
     * is 400 Bad Request when the add operation fails.
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
     * tests the `add` method of a controller by throwing a `CommandExecutionException`
     * when sending the command to the gateway, and verifying that the response status
     * code is `HttpServletResponse.SC_BAD_REQUEST`.
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
     * tests the add method of a controller by providing invalid input parameters, ensuring
     * that the method returns a BAD_REQUEST status response.
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

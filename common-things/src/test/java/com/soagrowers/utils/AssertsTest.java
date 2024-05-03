package com.soagrowers.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * in the provided Java file contains several tests for the `Asserts` class, including
 * testing the `setAssertsTo()` method, `isNotEmpty()`, and `areNotEmpty()` methods.
 * These tests verify that the `Asserts` instance is properly configured and that its
 * methods behave as expected when given different inputs.
 */
public class AssertsTest {

    /**
     * sets the Asserts instance to true, enabling assertion checks throughout the code.
     */
    @Before
    public void setup(){
        Asserts.INSTANCE.setAssertsTo(true);
    }

    /**
     * sets and verifies the state of the `Asserts.INSTANCE.setAssertsTo()` method, which
     * enables or disables asserts on a Java application.
     */
    @Test
    public void testSetAsserts(){

        Asserts.INSTANCE.setAssertsTo(true);
        assertTrue(Asserts.INSTANCE.isAssertsOn());

        Asserts.INSTANCE.setAssertsTo(false);
        assertFalse(Asserts.INSTANCE.isAssertsOn());
    }

    /**
     * tests whether a given string is not empty using the `Asserts.INSTANCE.isNotEmpty()`
     * method. It throws an `AssertionError` if the argument is null or empty.
     */
    @Test
    public void testIsNotEmpty(){

        Asserts.INSTANCE.isNotEmpty(new String("test"));

        try {
            Asserts.INSTANCE.isNotEmpty(null);
            assertTrue(false);
        } catch (AssertionError ae){
            assertEquals(Asserts.INSTANCE.UNEXPECTED_NULL, ae.getMessage());
        }

        try {
            Asserts.INSTANCE.isNotEmpty(Asserts.EMPTY_STRING);
            assertTrue(false);
        } catch (AssertionError ae){
            assertEquals(Asserts.INSTANCE.UNEXPECTED_EMPTY_STRING, ae.getMessage());
        }
    }

    /**
     * tests if an array list is not empty by verifying that it contains at least one
     * non-empty element. If the list is empty, it throws an `AssertionError`.
     */
    @Test
    public void testAreNotEmpty(){

        Asserts.INSTANCE.areNotEmpty(Arrays.asList("test", "test"));

        try {
            String id = UUID.randomUUID().toString();
            String name = Asserts.EMPTY_STRING;
            Asserts.INSTANCE.areNotEmpty(Arrays.asList(id, name));
            assertTrue(false);
        } catch (AssertionError ae){
            assertEquals(ae.getMessage(), Asserts.UNEXPECTED_EMPTY_STRING);
        }

        try {
            String id = null;
            String name = Asserts.EMPTY_STRING;
            Asserts.INSTANCE.areNotEmpty(Arrays.asList(id, name));
            assertTrue(false);
        } catch (AssertionError ae){
            assertEquals(ae.getMessage(), Asserts.UNEXPECTED_NULL);
        }
    }

    /**
     * tests whether a list is empty using the `Asserts.isNotEmpty()` method. It throws
     * an `IllegalArgumentException` if the list is empty and passes otherwise.
     */
    @Test
    public void testIsNotEmptyWithAList(){
        String id = UUID.randomUUID().toString();
        String name = Asserts.EMPTY_STRING;

        try {
            Asserts.INSTANCE.isNotEmpty(Arrays.asList(id, name));
            assertTrue(false);
        } catch (IllegalArgumentException ia){
            assertTrue(true);
        }
    }
}

package com.soagrowers.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ben on 02/03/16.
 */
public enum Asserts {

    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(Asserts.class);

    private static boolean active = false;

    public static final String EMPTY_STRING = "";
    public static final String UNEXPECTED_NULL = "Unexpected Null Parameter";
    public static final String UNEXPECTED_EMPTY_STRING = "Unexpected Null Parameter";
    public static final String NOT_TRUE = "Should have been TRUE but wasn't";
    public static final String NOT_FALSE = "Should have been FALSE but wasn't";
    public static final String USED_WRONG_METHOD = "I think you used the wrong Asserts method!";

    private static Asserts instance;

    private Asserts() {
    }


    /**
     * Checks if a provided boolean value is true or false. If it's false, it logs a
     * warning message and throws an `AssertionError`.
     * 
     * @param trueism boolean value that is being checked against the active state of the
     * function, with an error message and throw of AssertionError occurring if it is not
     * true.
     */
    public void isTrue(boolean trueism) {
        if (active && trueism == false) {
            LOG.warn(NOT_TRUE);
            throw new AssertionError(NOT_TRUE);
        }
    }

    /**
     * Checks whether a given boolean value is true or false, and raises an `AssertionError`
     * if it is not false.
     * 
     * @param falsehood boolean value that is checked against the active status of the
     * function, with any value other than `true` causing an AssertionError to be thrown.
     */
    public void isFalse(boolean falsehood) {
        if (active && falsehood == true) {
            LOG.warn(NOT_FALSE);
            throw new AssertionError(NOT_FALSE);
        }
    }

    /**
     * Checks if an object is not null, not a list, and not the empty string. If any of
     * these conditions are true, it logs an error message and throws an exception.
     * 
     * @param object argument that is passed to the `isNotEmpty` method, which is used
     * to check if it is not empty.
     */
    public void isNotEmpty(Object object) {

        if (active) {

            if (null == object) {
                LOG.debug(UNEXPECTED_NULL);
                throw new AssertionError(UNEXPECTED_NULL);
            }

            if (List.class.isAssignableFrom(object.getClass())) {
                LOG.debug(USED_WRONG_METHOD);
                throw new IllegalArgumentException(USED_WRONG_METHOD);
            }

            if (String.class.isAssignableFrom(object.getClass())) {
                if ((String) object == EMPTY_STRING) {
                    LOG.debug(UNEXPECTED_EMPTY_STRING);
                    throw new AssertionError(UNEXPECTED_EMPTY_STRING);
                }
            }
        }
    }

    /**
     * Verifies if a list of objects is not empty by iterating over its elements and
     * calling the `isNotEmpty` function on each one.
     * 
     * @param objects List of objects to check for emptiness.
     */
    public void areNotEmpty(List<Object> objects) {
        if (active) {
            for (Object object : objects) {
                isNotEmpty(object);
            }
        }
    }

    public boolean isAssertsOn() {
        return active;
    }

    /**
     * Sets the value of `Asserts.active` to the inputted `asserts` parameter.
     * 
     * @param asserts active state of the Asserts system, determining whether it should
     * be enabled or disabled.
     */
    public void setAssertsTo(boolean asserts) {
        Asserts.active = asserts;
    }
}

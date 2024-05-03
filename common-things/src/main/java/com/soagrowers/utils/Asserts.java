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
     * checks if a given boolean value is true or false. If it's not true, an AssertionError
     * is thrown with a message logged to the logger.
     * 
     * @param trueism boolean value that is compared to `active` and `false` in the if
     * statement, triggering the warning and error message when it is false.
     */
    public void isTrue(boolean trueism) {
        if (active && trueism == false) {
            LOG.warn(NOT_TRUE);
            throw new AssertionError(NOT_TRUE);
        }
    }

    /**
     * checks whether a given boolean value is true or false. If the value is true, an
     * error message is logged and an AssertionError is thrown.
     * 
     * @param falsehood boolean value that is being tested, with a specific focus on
     * whether it is true or false.
     */
    public void isFalse(boolean falsehood) {
        if (active && falsehood == true) {
            LOG.warn(NOT_FALSE);
            throw new AssertionError(NOT_FALSE);
        }
    }

    /**
     * checks if an object is not null and is not a list or a string that is empty. If
     * it finds any issues, it logs an error message and throws an exception.
     * 
     * @param object object that is being checked for certain properties, such as being
     * non-null and having a class that is assignable to List or String.
     * 
     * 	- If `active` is true, then `object` represents an object that may be assigned
     * to a List or String type.
     * 	- The `Class` of `object` can be any subclass of Object, indicating its potential
     * assignment to various types.
     * 	- If `null != object`, then the input is not null and has a non-null value.
     * 	- If `List.class.isAssignableFrom(object.getClass())`, then `object` is a List
     * that may be assigned to the method.
     * 	- If `String.class.isAssignableFrom(object.getClass())`, then `object` is a String
     * that may be assigned to the method.
     * 	- If `(String) object == EMPTY_STRING`, then `object` is a String with value equal
     * to the `EMPTY_STRING` constant.
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
     * checks whether a list of objects is not empty by iterating over its elements and
     * calling a nested function `isNotEmpty` on each object.
     * 
     * @param objects list of objects that are being checked for emptiness by the
     * `areNotEmpty()` method.
     * 
     * The function takes a list of objects as an input, which can have any combination
     * of attributes and methods. The list may contain duplicate elements or have other
     * structural properties that can be important for the function's operation.
     */
    public void areNotEmpty(List<Object> objects) {
        if (active) {
            for (Object object : objects) {
                isNotEmpty(object);
            }
        }
    }

    /**
     * checks whether the `active` flag is set to `true`. If it is, the function returns
     * `true`, otherwise it returns `false`.
     * 
     * @returns a boolean value indicating whether the `active` property is true.
     */
    public boolean isAssertsOn() {
        return active;
    }

    /**
     * sets the active status of the `Asserts` to the provided `boolean` value, affecting
     * the output of any subsequent `assert` statements.
     * 
     * @param asserts state of the active assertions in the code.
     */
    public void setAssertsTo(boolean asserts) {
        Asserts.active = asserts;
    }
}

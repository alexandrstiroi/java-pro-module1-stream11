package org.shtiroy.module1.hm01.junitlite;

public class TestAssertionError extends AssertionError{
    public TestAssertionError(String message) {
        super(message);
    }

    public TestAssertionError(String message, Throwable cause) {
        super(message, cause);
    }
}

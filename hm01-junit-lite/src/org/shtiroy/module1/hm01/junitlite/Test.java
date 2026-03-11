package org.shtiroy.module1.hm01.junitlite;

public class Test {
    private final TestResult testResult;
    private final String testName;
    private final Throwable exception;

    public Test(TestResult testResult, String testName, Throwable exception) {
        this.testResult = testResult;
        this.testName = testName;
        this.exception = exception;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public String getTestName() {
        return testName;
    }

    public Throwable getException() {
        return exception;
    }
}

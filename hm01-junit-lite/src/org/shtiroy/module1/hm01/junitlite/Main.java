package org.shtiroy.module1.hm01.junitlite;

public class Main {
    public static void main(String[] args) {
        Class<SimpleTests> simpleTestsClass = SimpleTests.class;
        TestRunner.runTests(simpleTestsClass).forEach((key, value) -> System.out.println(key + " " + value));
    }
}

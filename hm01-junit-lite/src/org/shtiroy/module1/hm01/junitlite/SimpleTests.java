package org.shtiroy.module1.hm01.junitlite;

import org.shtiroy.module1.hm01.junitlite.annotations.AfterEach;
import org.shtiroy.module1.hm01.junitlite.annotations.AfterSuite;
import org.shtiroy.module1.hm01.junitlite.annotations.BeforeEach;
import org.shtiroy.module1.hm01.junitlite.annotations.BeforeSuite;
import org.shtiroy.module1.hm01.junitlite.annotations.Disabled;
import org.shtiroy.module1.hm01.junitlite.annotations.Order;
import org.shtiroy.module1.hm01.junitlite.annotations.Test;

public class SimpleTests {
    private SimpleTests(){

    }

    @Test(name = "Login test", priority = 10)
    public void loginTest() {
        System.out.println("Login test executed");
    }

    @Disabled
    @Test(name="Login additional test")
    public void loginAdditionalTest() {
        System.out.println("Login additional test executed");
    }

    @Test(priority = 3)
    public void databaseTest() {
        System.out.println("Database test executed");
    }

    @Test()
    public void apiTest() {
        System.out.println("API test executed");
    }

    @Test(priority = 10)
    public void apiAdditionalTest() {
        System.out.println("API Additional test executed");
    }

    @Test()
    public void failedTest() {
        System.out.println("Failed test");
        throw new TestAssertionError("Failed test");
    }

    @Order(value = 1)
    @Test(name = "Error test", priority = 2)
    public void errorTest() {
        System.out.println("Error test");
        throw new NullPointerException("Error test");
    }

    @BeforeEach()
    public void testSettings() {
        System.out.println("@BeforeEach method run");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("@AfterEach method run");
    }

    @BeforeSuite
    public static void beforeSuiteMethod() {
        System.out.println("@BeforeSuite method run");
    }

    @AfterSuite
    public static void afterSuiteMethod() {
        System.out.println("@AfterSuite method run");
    }
}

package org.shtiroy.module1.hm01.junitlite;

import org.shtiroy.module1.hm01.junitlite.annotations.AfterEach;
import org.shtiroy.module1.hm01.junitlite.annotations.AfterSuite;
import org.shtiroy.module1.hm01.junitlite.annotations.BeforeEach;
import org.shtiroy.module1.hm01.junitlite.annotations.BeforeSuite;
import org.shtiroy.module1.hm01.junitlite.annotations.Disabled;
import org.shtiroy.module1.hm01.junitlite.annotations.Order;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRunner {
    public static Map<TestResult, List<Test>> runTests(Class<?> c){
        List<Method> beforeEachMethods = new ArrayList<>();
        List<Method> afterEachMethods = new ArrayList<>();
        List<Method> beforeSuiteMethods = new ArrayList<>();
        List<Method> afterSuiteMethods = new ArrayList<>();
        List<TestMethod> testMethods = new ArrayList<>();
        for (Method method : c.getMethods()) {
            if (method.isAnnotationPresent(org.shtiroy.module1.hm01.junitlite.annotations.Test.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@Test cannot be used on static method: " + method.getName());
                }
                org.shtiroy.module1.hm01.junitlite.annotations.Test testAnn = method.getAnnotation(org.shtiroy.module1.hm01.junitlite.annotations.Test.class);
                String nameFromAnn = testAnn.name();
                String displayName = nameFromAnn == null || nameFromAnn.isEmpty() ? method.getName() : nameFromAnn;
                int priority = testAnn.priority();
                if (priority < 0 || priority > 10) {
                    throw new BadTestClassError("Priority must be in 0 .. 10 " + method.getName());
                }
                Order orderAnn = method.getAnnotation(Order.class);
                int order = orderAnn == null ? 5 : orderAnn.value();
                if (order < 1 || order > 10) {
                    throw new BadTestClassError("@Order must be in 1..10 for method: " + method);
                }

                boolean disabled = method.isAnnotationPresent(Disabled.class);

                testMethods.add(new TestMethod(method, displayName, priority, order, disabled));
            }

            if (method.isAnnotationPresent(BeforeEach.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@BeforeEach cannot be used on static method: " + method.getName());
                }
                beforeEachMethods.add(method);
            }
            if (method.isAnnotationPresent(AfterEach.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@BeforeEach cannot be used on static method: " + method.getName());
                }
                afterEachMethods.add(method);
            }

            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@BeforeSuite can be used for static method: " + method.getName());
                }
                beforeSuiteMethods.add(method);
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@AfterSuite can be used for static method: " + method.getName());
                }
                afterSuiteMethods.add(method);
            }
        }
        Object object = createInstance(c);

        //Сортировка тестовых методов
        testMethods.sort((a1, a2) -> {
            int cmp = Integer.compare(a2.priority(), a1.priority());
            if (cmp != 0) return cmp;
            cmp = a1.displayName().compareTo(a2.displayName());
            if (cmp != 0) return cmp;
            return Integer.compare(a1.order(), a2.order());
        });

        Map<TestResult, List<Test>> result = new HashMap<>();

        for (Method method : beforeSuiteMethods) {
            try {
                runMethod(method, null);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        for (TestMethod testMethod : testMethods) {
            if (testMethod.disabled()) {
                result.computeIfAbsent(TestResult.SKIPPED, key -> new ArrayList<>()).add(new Test(TestResult.SKIPPED, testMethod.displayName(), null));
            }

            Throwable testThrowable = null;
            TestResult testResult;
            try {
                for (Method m : beforeEachMethods) {
                    runMethod(m, object);
                }

                runMethod(testMethod.method(), object);
                testResult = TestResult.SUCCESS;
            } catch (Throwable t) {
                if (t.getCause() instanceof TestAssertionError) {
                    testResult = TestResult.FAILED;
                } else {
                    testResult = TestResult.ERROR;
                }
                testThrowable = t;
            } finally {
                for (Method m : afterEachMethods) {
                    try {
                        runMethod(m, object);
                    } catch (Throwable throwable) {

                    }
                }
            }
            result.computeIfAbsent(testResult,key -> new ArrayList<>()).add(new Test(testResult, testMethod.displayName(), testThrowable));
        }

        for (Method method : afterSuiteMethods) {
            try {
                runMethod(method, null);
            } catch (Exception e)  {
                System.out.println(e.getMessage());
            }
        }

        return result;
    }

    //Проверка на доступность конструктора
    private static Object createInstance(Class<?> c) {
        try {
            Constructor<?> constructor = c.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new BadTestClassError("Test class must have a no-args constructor: " + c.getName(), e);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BadTestClassError("Cannot create test class instance: " + c.getName(), e);
        }
    }

    private static void runMethod(Method method, Object obj) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(obj);
    }
}

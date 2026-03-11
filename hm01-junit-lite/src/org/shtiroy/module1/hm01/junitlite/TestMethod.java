package org.shtiroy.module1.hm01.junitlite;

import java.lang.reflect.Method;

public record TestMethod(Method method, String displayName, int priority, int order, boolean disabled) {
}

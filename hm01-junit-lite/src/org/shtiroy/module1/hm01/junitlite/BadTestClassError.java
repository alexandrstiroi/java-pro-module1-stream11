package org.shtiroy.module1.hm01.junitlite;

public class BadTestClassError extends RuntimeException{
    public BadTestClassError(String message){
        super(message);
    }

    public BadTestClassError(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.juaracoding.handler;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 12/03/2025 19:24
@Last Modified 12/03/2025 19:24
Version 1.0
*/
public class ApiSubError {

    private String field;
    private String message;
    private Object rejectedValue;

    public ApiSubError(String field, String message, Object rejectedValue) {
        this.field = field;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }
}

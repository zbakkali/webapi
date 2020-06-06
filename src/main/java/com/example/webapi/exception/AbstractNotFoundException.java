package com.example.webapi.exception;

public abstract class AbstractNotFoundException extends RuntimeException {

    public AbstractNotFoundException(Object id, String entityName) {
        super(entityName + " not found: " + id);
    }
}
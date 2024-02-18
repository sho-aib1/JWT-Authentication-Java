package com.jwtauth.Exception;

public class ResouceNotFoundException extends RuntimeException {
    String field;
    String fieldValue;

    public ResouceNotFoundException(String field, String fieldValue) {
        super(String.format("%s not found with id  %s !", field, fieldValue));
        this.field = field;
        this.fieldValue = fieldValue;

    }
}

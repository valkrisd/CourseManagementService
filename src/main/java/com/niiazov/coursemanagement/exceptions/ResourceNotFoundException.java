package com.niiazov.coursemanagement.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}

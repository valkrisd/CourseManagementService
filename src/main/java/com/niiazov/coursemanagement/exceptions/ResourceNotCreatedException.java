package com.niiazov.coursemanagement.exceptions;

public class ResourceNotCreatedException extends RuntimeException {
    public ResourceNotCreatedException(String errorMsg) {
        super(errorMsg);
    }
}

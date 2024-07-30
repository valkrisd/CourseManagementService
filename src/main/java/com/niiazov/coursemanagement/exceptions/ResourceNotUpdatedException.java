package com.niiazov.coursemanagement.exceptions;

public class ResourceNotUpdatedException extends RuntimeException {
    public ResourceNotUpdatedException(String errorMsg) {
        super(errorMsg);
    }
}

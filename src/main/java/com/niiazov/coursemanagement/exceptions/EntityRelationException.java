package com.niiazov.coursemanagement.exceptions;

public class EntityRelationException extends RuntimeException {
    public EntityRelationException(String errorMsg) {
        super(errorMsg);
    }
}

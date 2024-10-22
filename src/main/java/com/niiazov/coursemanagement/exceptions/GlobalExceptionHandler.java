package com.niiazov.coursemanagement.exceptions;

import com.niiazov.coursemanagement.util.CourseManagementErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<CourseManagementErrorResponse> createErrorResponse(Exception ex, HttpStatus status) {
        CourseManagementErrorResponse errorResponse = new CourseManagementErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CourseManagementErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return createErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotCreatedException.class)
    public ResponseEntity<CourseManagementErrorResponse> handleResourceNotCreatedException(ResourceNotCreatedException ex) {
        return createErrorResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotUpdatedException.class)
    public ResponseEntity<CourseManagementErrorResponse> handleResourceNotUpdatedException(ResourceNotUpdatedException ex) {
        return createErrorResponse(ex, HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

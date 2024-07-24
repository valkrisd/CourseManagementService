package com.niiazov.coursemanagement.exceptions;

import com.niiazov.coursemanagement.util.CourseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CourseErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        CourseErrorResponse errorResponse = new CourseErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotCreatedException.class)
    public ResponseEntity<CourseErrorResponse> handleResourceNotCreatedException(ResourceNotCreatedException ex) {
        CourseErrorResponse errorResponse = new CourseErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotUpdatedException.class)
    public ResponseEntity<CourseErrorResponse> handleResourceNotUpdatedException(ResourceNotUpdatedException ex) {
        CourseErrorResponse errorResponse = new CourseErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_MODIFIED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        CourseErrorResponse errorResponse = new CourseErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.example.JWT.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestControllerAdvice
public class GlobalException {

    // Handle custom exceptions (UserAlreadyExistsException)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        // Create a response body with a meaningful error message
        ErrorResponse errorResponse = new ErrorResponse("401", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle UsernameNotFoundException (Spring's built-in exception)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        // Create a response body with a meaningful error message
        ErrorResponse errorResponse = new ErrorResponse("401", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Generic error handler (optional)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("500", "An unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Error response structure
    public static class ErrorResponse {
        private String errorCode;
        private String errorMessage;

        public ErrorResponse(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}

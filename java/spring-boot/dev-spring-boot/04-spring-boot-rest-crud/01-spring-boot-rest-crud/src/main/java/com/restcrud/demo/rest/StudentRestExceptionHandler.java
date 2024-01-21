package com.restcrud.demo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentRestExceptionHandler {

//        add exception for handling code here, its a global one

    //        add an exception handler using @ExceptionHandler

    @ExceptionHandler
    // exception handler method, StudentErrorResponse = type of the response body, exception type to handle
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {

//        create a StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

//        return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // error is the body and the status code

    }

    //    add another exception handler ... to catch any exception (catch all)
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exc) { // handle generic exception

//        create a StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

//        return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // error is the body and the status code
    }

}

package dev.jola.VacTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.DefaultPartHttpMessageReader;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmployeeNotFountExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity employeeNotFoundException(EmployeeNotFoundException exception){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());

    }

}

package com.example.nbastat.analyticservice.common;

import com.example.nbastat.analyticservice.exception.DataNotFoundException;
import com.example.nbastat.analyticservice.exception.ServiceUnAvailableException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceUnAvailableException.class)
    public ResponseEntity<ExceptionWrapper> handleServiceUnAvailableException(ServiceUnAvailableException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionWrapper(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionWrapper> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWrapper(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionWrapper> handleDataNotFoundException(DataNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionWrapper(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

}
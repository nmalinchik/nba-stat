package com.example.nbastat.analyticservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String message) {
        super(message);
    }
}

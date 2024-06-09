package com.example.nbastat.playerservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String message) {
        super(message);
    }
}

package com.example.nbastat.teamservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String message) {
        super(message);
    }
}

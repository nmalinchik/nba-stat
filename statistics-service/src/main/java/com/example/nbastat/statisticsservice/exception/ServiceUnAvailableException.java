package com.example.nbastat.statisticsservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String message) {
        super(message);
    }
}

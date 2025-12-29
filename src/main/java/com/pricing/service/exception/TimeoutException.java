package com.pricing.service.exception;

public class TimeoutException extends RuntimeException {
    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}

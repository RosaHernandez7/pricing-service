package com.pricing.service.exception;

public class ExternalClientException extends RuntimeException {
    public ExternalClientException(String message) {
        super(message);
    }
}
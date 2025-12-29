package com.pricing.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.pricing.service.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> badRequest(Exception ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(ExternalClientException.class)
    public ResponseEntity<ErrorResponse> external4xx(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorResponse("EXTERNAL_4XX", ex.getMessage()));
    }

    @ExceptionHandler(ExternalServerException.class)
    public ResponseEntity<ErrorResponse> external5xx(Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponse("EXTERNAL_5XX", ex.getMessage()));
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<ErrorResponse> timeout(Exception ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                .body(new ErrorResponse("TIMEOUT", ex.getMessage()));
    }

    @ExceptionHandler(ProviderTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleTimeout(ProviderTimeoutException ex) {
        return ResponseEntity
                .status(HttpStatus.GATEWAY_TIMEOUT) // 504
                .body(new ErrorResponse("TIMEOUT", ex.getMessage()));
    }
}
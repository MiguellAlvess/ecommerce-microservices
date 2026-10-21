package br.com.miguelalves.order.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.miguelalves.order.dto.ApiErrorDTO;
import br.com.miguelalves.order.exception.ExternalServiceException;
import br.com.miguelalves.order.exception.OrderNotFoundException;
import br.com.miguelalves.order.exception.OrderValidationException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(OrderValidationException.class)
    public ResponseEntity<ApiErrorDTO> handleValidation(
            OrderValidationException exception,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.UNPROCESSABLE_CONTENT, exception.getCode(), exception.getMessage(), request);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleNotFound(
            OrderNotFoundException exception,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, "ORDER_NOT_FOUND", exception.getMessage(), request);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ApiErrorDTO> handleExternalService(
            ExternalServiceException exception,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, exception.getCode(), exception.getMessage(), request);
    }

    private ResponseEntity<ApiErrorDTO> buildResponse(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request) {
        ApiErrorDTO body = new ApiErrorDTO(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                code,
                message,
                request.getRequestURI());

        return ResponseEntity.status(status).body(body);
    }
}

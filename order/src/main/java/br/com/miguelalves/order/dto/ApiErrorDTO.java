package br.com.miguelalves.order.dto;

import java.time.Instant;

public record ApiErrorDTO(
        Instant timestamp,
        int status,
        String error,
        String code,
        String message,
        String path) {
}

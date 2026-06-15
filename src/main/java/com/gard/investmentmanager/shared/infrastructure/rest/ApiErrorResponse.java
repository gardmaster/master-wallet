package com.gard.investmentmanager.shared.infrastructure.rest;

public record ApiErrorResponse(
        String code,
        String message
) {
}
package com.gard.investmentmanager.shared.infrastructure.rest;

public record ProblemDetailsViolation(
        String field,
        String message
) {
}
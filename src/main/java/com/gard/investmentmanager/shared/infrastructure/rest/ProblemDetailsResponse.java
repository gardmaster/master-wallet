package com.gard.investmentmanager.shared.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProblemDetailsResponse(
        String type,
        String title,
        int status,
        String detail,
        String instance,
        List<ProblemDetailsViolation> violations
) {
}
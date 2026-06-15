package com.gard.investmentmanager.shared.infrastructure.rest;

import java.util.List;

public record ProblemDetailsResponse(
        String type,
        String title,
        int status,
        String detail,
        String instance,
        List<ProblemDetailsViolation> violations
) {
}
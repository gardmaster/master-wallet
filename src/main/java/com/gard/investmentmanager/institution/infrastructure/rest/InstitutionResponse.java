package com.gard.investmentmanager.institution.infrastructure.rest;

import com.gard.investmentmanager.institution.domain.InstitutionType;

import java.time.Instant;

public record InstitutionResponse(
        Long id,
        String name,
        InstitutionType institutionType,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
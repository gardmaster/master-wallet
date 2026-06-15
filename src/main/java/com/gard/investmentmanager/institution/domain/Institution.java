package com.gard.investmentmanager.institution.domain;

import java.time.Instant;

public record Institution(
        Long id,
        Long userId,
        String name,
        InstitutionType institutionType,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
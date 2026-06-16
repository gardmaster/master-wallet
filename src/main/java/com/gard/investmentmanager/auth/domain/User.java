package com.gard.investmentmanager.auth.domain;

import java.time.Instant;

public record User(
        Long id,
        String name,
        String email,
        String externalSubject,
        Instant createdAt,
        Instant updatedAt
) {
}
package com.gard.investmentmanager.account.infrastructure.rest;

import com.gard.investmentmanager.account.domain.AccountType;

import java.time.Instant;

public record AccountResponse(
        Long id,
        Long institutionId,
        String name,
        AccountType accountType,
        String baseCurrency,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
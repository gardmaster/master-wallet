package com.gard.investmentmanager.account.domain;

import java.time.Instant;

public record InvestmentAccount(
        Long id,
        Long userId,
        Long institutionId,
        String name,
        AccountType accountType,
        String baseCurrency,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
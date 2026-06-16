package com.gard.investmentmanager.account.application.port.in;

import com.gard.investmentmanager.account.domain.AccountType;

public record CreateAccountCommand(
        Long institutionId,
        String name,
        AccountType accountType,
        String baseCurrency,
        String notes
) {
}
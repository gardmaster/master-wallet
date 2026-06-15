package com.gard.investmentmanager.account.infrastructure.rest;

import com.gard.investmentmanager.account.domain.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateAccountRequest(
        @NotNull Long institutionId,
        @NotBlank @Size(max = 150) String name,
        @NotNull AccountType accountType,
        @NotBlank @Size(min = 3, max = 3) String baseCurrency,
        String notes
) {
}
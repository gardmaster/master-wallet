package com.gard.investmentmanager.account.infrastructure.rest;

import com.gard.investmentmanager.account.domain.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateAccountRequest(
        @NotNull(message = "{validation.not-null}")
        Long institutionId,

        @NotBlank(message = "{validation.not-blank}")
        @Size(max = 150, message = "{validation.size.max}")
        String name,

        @NotNull(message = "{validation.not-null}")
        AccountType accountType,

        @NotBlank(message = "{validation.not-blank}")
        @Pattern(regexp = "^[A-Za-z]{3}$", message = "{validation.currency-code}")
        String baseCurrency,

        String notes
) {
}
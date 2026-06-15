package com.gard.investmentmanager.institution.infrastructure.rest;

import com.gard.investmentmanager.institution.domain.InstitutionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateInstitutionRequest(
        @NotNull(message = "{validation.not-null}")
        Long userId,

        @NotBlank(message = "{validation.not-blank}")
        @Size(max = 150, message = "{validation.size.max}")
        String name,

        @NotNull(message = "{validation.not-null}")
        InstitutionType institutionType,

        String notes
) {
}
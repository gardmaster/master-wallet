package com.gard.investmentmanager.institution.infrastructure.rest;

import com.gard.investmentmanager.institution.domain.InstitutionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateInstitutionRequest(
        @NotNull Long userId,
        @NotBlank @Size(max = 150) String name,
        @NotNull InstitutionType institutionType,
        String notes
) {
}
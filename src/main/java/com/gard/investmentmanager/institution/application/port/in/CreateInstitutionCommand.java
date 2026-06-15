package com.gard.investmentmanager.institution.application.port.in;

import com.gard.investmentmanager.institution.domain.InstitutionType;

public record CreateInstitutionCommand(
        Long userId,
        String name,
        InstitutionType institutionType,
        String notes
) {
}
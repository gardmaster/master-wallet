package com.gard.investmentmanager.institution.application.port.in;

import com.gard.investmentmanager.institution.domain.InstitutionType;

public record CreateInstitutionCommand(
        String name,
        InstitutionType institutionType,
        String notes
) {
}
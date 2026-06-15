package com.gard.investmentmanager.institution.application.port.in;

import com.gard.investmentmanager.institution.domain.InstitutionType;

public record UpdateInstitutionCommand(
        String name,
        InstitutionType institutionType,
        String notes
) {
}
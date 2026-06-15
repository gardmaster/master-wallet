package com.gard.investmentmanager.institution.application.port.in;

import com.gard.investmentmanager.institution.domain.Institution;

public interface UpdateInstitutionUC {

    Institution execute(Long institutionId, UpdateInstitutionCommand command);
}
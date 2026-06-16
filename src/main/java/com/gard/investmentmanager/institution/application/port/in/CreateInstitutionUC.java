package com.gard.investmentmanager.institution.application.port.in;

import com.gard.investmentmanager.institution.domain.Institution;

public interface CreateInstitutionUC {

    Institution execute(Long currentUserId, CreateInstitutionCommand command);
}
package com.gard.investmentmanager.institution.application.port.in;

import com.gard.investmentmanager.institution.domain.Institution;

import java.util.List;

public interface ListInstitutionsUC {

    List<Institution> execute();
}
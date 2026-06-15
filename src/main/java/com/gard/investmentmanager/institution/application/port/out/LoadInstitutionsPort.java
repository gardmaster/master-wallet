package com.gard.investmentmanager.institution.application.port.out;

import com.gard.investmentmanager.institution.domain.Institution;

import java.util.List;

public interface LoadInstitutionsPort {

    List<Institution> loadAllOrderedByName();
}
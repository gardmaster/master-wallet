package com.gard.investmentmanager.institution.application.service;

import com.gard.investmentmanager.institution.application.port.in.ListInstitutionsUC;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.institution.domain.Institution;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListInstitutionsService implements ListInstitutionsUC {

    private final InstitutionPersistencePort institutionPersistencePort;

    public ListInstitutionsService(InstitutionPersistencePort institutionPersistencePort) {
        this.institutionPersistencePort = institutionPersistencePort;
    }

    @Override
    public List<Institution> execute(Long currentUserId) {
        return institutionPersistencePort.findAllOrderedByUserIdAndName(currentUserId);
    }
}
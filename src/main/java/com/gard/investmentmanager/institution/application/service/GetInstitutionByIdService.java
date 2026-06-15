package com.gard.investmentmanager.institution.application.service;

import com.gard.investmentmanager.institution.application.port.in.GetInstitutionByIdUC;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.institution.domain.Institution;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetInstitutionByIdService implements GetInstitutionByIdUC {

    private final InstitutionPersistencePort institutionPersistencePort;

    public GetInstitutionByIdService(InstitutionPersistencePort institutionPersistencePort) {
        this.institutionPersistencePort = institutionPersistencePort;
    }

    @Override
    public Institution execute(Long institutionId) {
        return institutionPersistencePort.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found: " + institutionId));
    }
}
package com.gard.investmentmanager.institution.application.service;

import com.gard.investmentmanager.institution.application.port.in.DeleteInstitutionUC;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteInstitutionService implements DeleteInstitutionUC {

    private final InstitutionPersistencePort institutionPersistencePort;

    public DeleteInstitutionService(InstitutionPersistencePort institutionPersistencePort) {
        this.institutionPersistencePort = institutionPersistencePort;
    }

    @Override
    @Transactional
    public void execute(Long institutionId) {
        if (institutionPersistencePort.findById(institutionId).isEmpty()) {
            throw new ResourceNotFoundException("Institution not found: " + institutionId);
        }

        institutionPersistencePort.deleteById(institutionId);
    }
}
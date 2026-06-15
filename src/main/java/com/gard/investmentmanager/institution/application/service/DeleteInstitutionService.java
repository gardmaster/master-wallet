package com.gard.investmentmanager.institution.application.service;

import com.gard.investmentmanager.institution.application.port.in.DeleteInstitutionUC;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteInstitutionService implements DeleteInstitutionUC {

    private final InstitutionPersistencePort institutionPersistencePort;
    private final MessageResolver messageResolver;

    public DeleteInstitutionService(
            InstitutionPersistencePort institutionPersistencePort,
            MessageResolver messageResolver
    ) {
        this.institutionPersistencePort = institutionPersistencePort;
        this.messageResolver = messageResolver;
    }

    @Override
    @Transactional
    public void execute(Long institutionId) {
        if (institutionPersistencePort.findById(institutionId).isEmpty()) {
            throw new ResourceNotFoundException(
                    messageResolver.get("error.institution.not-found", institutionId)
            );
        }

        institutionPersistencePort.softDeleteById(institutionId);
    }
}
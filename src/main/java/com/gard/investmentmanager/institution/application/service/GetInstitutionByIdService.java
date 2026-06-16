package com.gard.investmentmanager.institution.application.service;

import com.gard.investmentmanager.institution.application.port.in.GetInstitutionByIdUC;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.institution.domain.Institution;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetInstitutionByIdService implements GetInstitutionByIdUC {

    private final InstitutionPersistencePort institutionPersistencePort;
    private final MessageResolver messageResolver;

    public GetInstitutionByIdService(
            InstitutionPersistencePort institutionPersistencePort,
            MessageResolver messageResolver
    ) {
        this.institutionPersistencePort = institutionPersistencePort;
        this.messageResolver = messageResolver;
    }

    @Override
    public Institution execute(Long currentUserId, Long institutionId) {
        return institutionPersistencePort.findByIdAndUserId(institutionId, currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageResolver.get("error.institution.not-found", institutionId)
                ));
    }
}
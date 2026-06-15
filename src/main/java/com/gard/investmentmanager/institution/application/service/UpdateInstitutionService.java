package com.gard.investmentmanager.institution.application.service;

import com.gard.investmentmanager.institution.application.port.in.UpdateInstitutionCommand;
import com.gard.investmentmanager.institution.application.port.in.UpdateInstitutionUC;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.institution.domain.Institution;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class UpdateInstitutionService implements UpdateInstitutionUC {

    private final InstitutionPersistencePort institutionPersistencePort;

    public UpdateInstitutionService(InstitutionPersistencePort institutionPersistencePort) {
        this.institutionPersistencePort = institutionPersistencePort;
    }

    @Override
    @Transactional
    public Institution execute(Long institutionId, UpdateInstitutionCommand command) {
        Institution current = institutionPersistencePort.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found: " + institutionId));

        Institution updated = new Institution(
                current.id(),
                current.userId(),
                command.name().trim(),
                command.institutionType(),
                normalizeNullable(command.notes()),
                current.createdAt(),
                Instant.now()
        );

        return institutionPersistencePort.save(updated);
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
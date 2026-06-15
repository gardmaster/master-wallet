package com.gard.investmentmanager.institution.application.service;

import com.gard.investmentmanager.institution.application.port.in.CreateInstitutionCommand;
import com.gard.investmentmanager.institution.application.port.in.CreateInstitutionUC;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.institution.domain.Institution;
import com.gard.investmentmanager.shared.application.port.out.LoadUserPort;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class CreateInstitutionService implements CreateInstitutionUC {

    private final InstitutionPersistencePort institutionPersistencePort;
    private final LoadUserPort loadUserPort;

    public CreateInstitutionService(
            InstitutionPersistencePort institutionPersistencePort,
            LoadUserPort loadUserPort
    ) {
        this.institutionPersistencePort = institutionPersistencePort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    @Transactional
    public Institution execute(CreateInstitutionCommand command) {
        if (!loadUserPort.existsById(command.userId())) {
            throw new ResourceNotFoundException("User not found: " + command.userId());
        }

        Instant now = Instant.now();

        Institution institution = new Institution(
                null,
                command.userId(),
                command.name().trim(),
                command.institutionType(),
                normalizeNullable(command.notes()),
                now,
                now
        );

        return institutionPersistencePort.save(institution);
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
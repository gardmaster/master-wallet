package com.gard.investmentmanager.institution.application.service;

import com.gard.investmentmanager.institution.application.port.in.CreateInstitutionCommand;
import com.gard.investmentmanager.institution.application.port.in.CreateInstitutionUC;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.institution.domain.Institution;
import com.gard.investmentmanager.shared.application.port.out.LoadUserPort;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class CreateInstitutionService implements CreateInstitutionUC {

    private final InstitutionPersistencePort institutionPersistencePort;
    private final LoadUserPort loadUserPort;
    private final MessageResolver messageResolver;

    public CreateInstitutionService(
            InstitutionPersistencePort institutionPersistencePort,
            LoadUserPort loadUserPort,
            MessageResolver messageResolver
    ) {
        this.institutionPersistencePort = institutionPersistencePort;
        this.loadUserPort = loadUserPort;
        this.messageResolver = messageResolver;
    }

    @Override
    @Transactional
    public Institution execute(Long currentUserId, CreateInstitutionCommand command) {
        if (!loadUserPort.existsById(currentUserId)) {
            throw new ResourceNotFoundException(
                    messageResolver.get("error.user.not-found", currentUserId)
            );
        }

        Instant now = Instant.now();

        Institution institution = new Institution(
                null,
                currentUserId,
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
package com.gard.investmentmanager.account.application.service;

import com.gard.investmentmanager.account.application.port.in.CreateAccountCommand;
import com.gard.investmentmanager.account.application.port.in.CreateAccountUC;
import com.gard.investmentmanager.account.application.port.out.AccountPersistencePort;
import com.gard.investmentmanager.account.application.port.out.LoadAccountReferencePort;
import com.gard.investmentmanager.account.domain.InvestmentAccount;
import com.gard.investmentmanager.shared.application.port.out.LoadUserPort;
import com.gard.investmentmanager.shared.domain.BusinessException;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class CreateAccountService implements CreateAccountUC {

    private final AccountPersistencePort accountPersistencePort;
    private final LoadAccountReferencePort loadAccountReferencePort;
    private final LoadUserPort loadUserPort;
    private final MessageResolver messageResolver;

    public CreateAccountService(
            AccountPersistencePort accountPersistencePort,
            LoadAccountReferencePort loadAccountReferencePort,
            LoadUserPort loadUserPort,
            MessageResolver messageResolver
    ) {
        this.accountPersistencePort = accountPersistencePort;
        this.loadAccountReferencePort = loadAccountReferencePort;
        this.loadUserPort = loadUserPort;
        this.messageResolver = messageResolver;
    }

    @Override
    @Transactional
    public InvestmentAccount execute(Long currentUserId, CreateAccountCommand command) {
        if (!loadUserPort.existsById(currentUserId)) {
            throw new ResourceNotFoundException(
                    messageResolver.get("error.user.not-found", currentUserId)
            );
        }

        if (!loadAccountReferencePort.institutionBelongsToUser(command.institutionId(), currentUserId)) {
            throw new BusinessException(
                    messageResolver.get("error.institution.not-belong-to-user", command.institutionId())
            );
        }

        Instant now = Instant.now();

        InvestmentAccount account = new InvestmentAccount(
                null,
                currentUserId,
                command.institutionId(),
                command.name().trim(),
                command.accountType(),
                command.baseCurrency().trim().toUpperCase(),
                normalizeNullable(command.notes()),
                now,
                now
        );

        return accountPersistencePort.save(account);
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
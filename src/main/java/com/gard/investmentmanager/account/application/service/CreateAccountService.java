package com.gard.investmentmanager.account.application.service;

import com.gard.investmentmanager.account.application.port.in.CreateAccountCommand;
import com.gard.investmentmanager.account.application.port.in.CreateAccountUC;
import com.gard.investmentmanager.account.application.port.out.AccountPersistencePort;
import com.gard.investmentmanager.account.application.port.out.LoadAccountReferencePort;
import com.gard.investmentmanager.account.domain.InvestmentAccount;
import com.gard.investmentmanager.shared.application.port.out.LoadUserPort;
import com.gard.investmentmanager.shared.domain.BusinessException;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class CreateAccountService implements CreateAccountUC {

    private final AccountPersistencePort accountPersistencePort;
    private final LoadAccountReferencePort loadAccountReferencePort;
    private final LoadUserPort loadUserPort;

    public CreateAccountService(
            AccountPersistencePort accountPersistencePort,
            LoadAccountReferencePort loadAccountReferencePort,
            LoadUserPort loadUserPort
    ) {
        this.accountPersistencePort = accountPersistencePort;
        this.loadAccountReferencePort = loadAccountReferencePort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    @Transactional
    public InvestmentAccount execute(CreateAccountCommand command) {
        if (!loadUserPort.existsById(command.userId())) {
            throw new ResourceNotFoundException("User not found: " + command.userId());
        }

        if (!loadAccountReferencePort.institutionBelongsToUser(command.institutionId(), command.userId())) {
            throw new BusinessException("Institution does not belong to user: " + command.institutionId());
        }

        Instant now = Instant.now();

        InvestmentAccount account = new InvestmentAccount(
                null,
                command.userId(),
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
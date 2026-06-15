package com.gard.investmentmanager.account.application.service;

import com.gard.investmentmanager.account.application.port.in.UpdateAccountCommand;
import com.gard.investmentmanager.account.application.port.in.UpdateAccountUC;
import com.gard.investmentmanager.account.application.port.out.AccountPersistencePort;
import com.gard.investmentmanager.account.application.port.out.LoadAccountReferencePort;
import com.gard.investmentmanager.account.domain.InvestmentAccount;
import com.gard.investmentmanager.shared.domain.BusinessException;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class UpdateAccountService implements UpdateAccountUC {

    private final AccountPersistencePort accountPersistencePort;
    private final LoadAccountReferencePort loadAccountReferencePort;
    private final MessageResolver messageResolver;

    public UpdateAccountService(
            AccountPersistencePort accountPersistencePort,
            LoadAccountReferencePort loadAccountReferencePort,
            MessageResolver messageResolver
    ) {
        this.accountPersistencePort = accountPersistencePort;
        this.loadAccountReferencePort = loadAccountReferencePort;
        this.messageResolver = messageResolver;
    }

    @Override
    @Transactional
    public InvestmentAccount execute(Long accountId, UpdateAccountCommand command) {
        InvestmentAccount current = accountPersistencePort.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageResolver.get("error.account.not-found", accountId)
                ));

        if (!loadAccountReferencePort.institutionBelongsToUser(command.institutionId(), current.userId())) {
            throw new BusinessException(
                    messageResolver.get("error.institution.not-belong-to-user", command.institutionId())
            );
        }

        InvestmentAccount updated = new InvestmentAccount(
                current.id(),
                current.userId(),
                command.institutionId(),
                command.name().trim(),
                command.accountType(),
                command.baseCurrency().trim().toUpperCase(),
                normalizeNullable(command.notes()),
                current.createdAt(),
                Instant.now()
        );

        return accountPersistencePort.save(updated);
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
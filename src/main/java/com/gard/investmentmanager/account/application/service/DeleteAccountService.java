package com.gard.investmentmanager.account.application.service;

import com.gard.investmentmanager.account.application.port.in.DeleteAccountUC;
import com.gard.investmentmanager.account.application.port.out.AccountPersistencePort;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteAccountService implements DeleteAccountUC {

    private final AccountPersistencePort accountPersistencePort;
    private final MessageResolver messageResolver;

    public DeleteAccountService(
            AccountPersistencePort accountPersistencePort,
            MessageResolver messageResolver
    ) {
        this.accountPersistencePort = accountPersistencePort;
        this.messageResolver = messageResolver;
    }

    @Override
    @Transactional
    public void execute(Long accountId) {
        if (accountPersistencePort.findById(accountId).isEmpty()) {
            throw new ResourceNotFoundException(
                    messageResolver.get("error.account.not-found", accountId)
            );
        }

        accountPersistencePort.softDeleteById(accountId);
    }
}
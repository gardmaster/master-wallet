package com.gard.investmentmanager.account.application.service;

import com.gard.investmentmanager.account.application.port.in.GetAccountByIdUC;
import com.gard.investmentmanager.account.application.port.out.AccountPersistencePort;
import com.gard.investmentmanager.account.domain.InvestmentAccount;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetAccountByIdService implements GetAccountByIdUC {

    private final AccountPersistencePort accountPersistencePort;
    private final MessageResolver messageResolver;

    public GetAccountByIdService(
            AccountPersistencePort accountPersistencePort,
            MessageResolver messageResolver
    ) {
        this.accountPersistencePort = accountPersistencePort;
        this.messageResolver = messageResolver;
    }

    @Override
    public InvestmentAccount execute(Long currentUserId, Long accountId) {
        return accountPersistencePort.findByIdAndUserId(accountId, currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageResolver.get("error.account.not-found", accountId)
                ));
    }
}
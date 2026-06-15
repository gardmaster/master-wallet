package com.gard.investmentmanager.account.application.service;

import com.gard.investmentmanager.account.application.port.in.GetAccountByIdUC;
import com.gard.investmentmanager.account.application.port.out.AccountPersistencePort;
import com.gard.investmentmanager.account.domain.InvestmentAccount;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetAccountByIdService implements GetAccountByIdUC {

    private final AccountPersistencePort accountPersistencePort;

    public GetAccountByIdService(AccountPersistencePort accountPersistencePort) {
        this.accountPersistencePort = accountPersistencePort;
    }

    @Override
    public InvestmentAccount execute(Long accountId) {
        return accountPersistencePort.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));
    }
}
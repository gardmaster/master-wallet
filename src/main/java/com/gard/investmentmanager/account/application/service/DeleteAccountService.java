package com.gard.investmentmanager.account.application.service;

import com.gard.investmentmanager.account.application.port.in.DeleteAccountUC;
import com.gard.investmentmanager.account.application.port.out.AccountPersistencePort;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteAccountService implements DeleteAccountUC {

    private final AccountPersistencePort accountPersistencePort;

    public DeleteAccountService(AccountPersistencePort accountPersistencePort) {
        this.accountPersistencePort = accountPersistencePort;
    }

    @Override
    @Transactional
    public void execute(Long accountId) {
        if (accountPersistencePort.findById(accountId).isEmpty()) {
            throw new ResourceNotFoundException("Account not found: " + accountId);
        }

        accountPersistencePort.deleteById(accountId);
    }
}
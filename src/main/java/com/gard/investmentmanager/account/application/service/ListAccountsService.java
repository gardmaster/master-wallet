package com.gard.investmentmanager.account.application.service;

import com.gard.investmentmanager.account.application.port.in.ListAccountsUC;
import com.gard.investmentmanager.account.application.port.out.AccountPersistencePort;
import com.gard.investmentmanager.account.domain.InvestmentAccount;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListAccountsService implements ListAccountsUC {

    private final AccountPersistencePort accountPersistencePort;

    public ListAccountsService(AccountPersistencePort accountPersistencePort) {
        this.accountPersistencePort = accountPersistencePort;
    }

    @Override
    public List<InvestmentAccount> execute(Long currentUserId) {
        return accountPersistencePort.findAllOrderedByUserIdAndName(currentUserId);
    }
}
package com.gard.investmentmanager.account.application.port.out;

import com.gard.investmentmanager.account.domain.InvestmentAccount;

import java.util.List;
import java.util.Optional;

public interface AccountPersistencePort {

    List<InvestmentAccount> findAllOrderedByName();

    Optional<InvestmentAccount> findById(Long accountId);

    InvestmentAccount save(InvestmentAccount account);

    void deleteById(Long accountId);
}
package com.gard.investmentmanager.account.application.port.out;

import com.gard.investmentmanager.account.domain.InvestmentAccount;

import java.util.List;
import java.util.Optional;

public interface AccountPersistencePort {

    List<InvestmentAccount> findAllOrderedByUserIdAndName(Long userId);

    Optional<InvestmentAccount> findByIdAndUserId(Long accountId, Long userId);

    InvestmentAccount save(InvestmentAccount account);

    void softDeleteById(Long accountId, Long userId);
}
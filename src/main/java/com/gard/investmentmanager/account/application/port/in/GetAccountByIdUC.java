package com.gard.investmentmanager.account.application.port.in;

import com.gard.investmentmanager.account.domain.InvestmentAccount;

public interface GetAccountByIdUC {

    InvestmentAccount execute(Long accountId);
}
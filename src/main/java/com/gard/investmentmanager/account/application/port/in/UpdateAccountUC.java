package com.gard.investmentmanager.account.application.port.in;

import com.gard.investmentmanager.account.domain.InvestmentAccount;

public interface UpdateAccountUC {

    InvestmentAccount execute(Long accountId, UpdateAccountCommand command);
}
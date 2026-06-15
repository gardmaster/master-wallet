package com.gard.investmentmanager.account.application.port.in;

import com.gard.investmentmanager.account.domain.InvestmentAccount;

public interface CreateAccountUC {

    InvestmentAccount execute(CreateAccountCommand command);
}
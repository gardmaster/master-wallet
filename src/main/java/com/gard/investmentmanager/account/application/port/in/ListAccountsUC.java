package com.gard.investmentmanager.account.application.port.in;

import com.gard.investmentmanager.account.domain.InvestmentAccount;

import java.util.List;

public interface ListAccountsUC {

    List<InvestmentAccount> execute();
}
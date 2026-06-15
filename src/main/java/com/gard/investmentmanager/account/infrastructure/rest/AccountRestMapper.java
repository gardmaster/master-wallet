package com.gard.investmentmanager.account.infrastructure.rest;

import com.gard.investmentmanager.account.domain.InvestmentAccount;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface AccountRestMapper {

    AccountResponse toResponse(InvestmentAccount account);

    List<AccountResponse> toResponseList(List<InvestmentAccount> accounts);
}
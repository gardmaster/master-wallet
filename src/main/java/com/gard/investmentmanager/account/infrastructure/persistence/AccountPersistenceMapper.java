package com.gard.investmentmanager.account.infrastructure.persistence;

import com.gard.investmentmanager.account.domain.InvestmentAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface AccountPersistenceMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "institutionId", source = "institution.id")
    InvestmentAccount toDomain(InvestmentAccountEntity entity);

    List<InvestmentAccount> toDomainList(List<InvestmentAccountEntity> entities);
}
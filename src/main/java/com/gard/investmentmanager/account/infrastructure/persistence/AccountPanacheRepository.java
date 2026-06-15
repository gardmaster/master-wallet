package com.gard.investmentmanager.account.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AccountPanacheRepository implements PanacheRepositoryBase<InvestmentAccountEntity, Long> {

    public List<InvestmentAccountEntity> listAllOrderedByName() {
        return find("order by name asc").list();
    }
}
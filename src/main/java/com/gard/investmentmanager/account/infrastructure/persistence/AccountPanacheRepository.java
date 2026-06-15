package com.gard.investmentmanager.account.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AccountPanacheRepository implements PanacheRepositoryBase<InvestmentAccountEntity, Long> {

    public List<InvestmentAccountEntity> listAllOrderedByName() {
        return find("deletedAt is null order by name asc").list();
    }

    public Optional<InvestmentAccountEntity> findActiveByIdOptional(Long accountId) {
        return find("id = ?1 and deletedAt is null", accountId).firstResultOptional();
    }

    public void softDeleteById(Long accountId, Instant deletedAt) {
        update("deletedAt = ?1 where id = ?2 and deletedAt is null", deletedAt, accountId);
    }
}
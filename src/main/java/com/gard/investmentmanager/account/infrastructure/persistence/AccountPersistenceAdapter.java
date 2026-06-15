package com.gard.investmentmanager.account.infrastructure.persistence;

import com.gard.investmentmanager.account.application.port.out.AccountPersistencePort;
import com.gard.investmentmanager.account.domain.InvestmentAccount;
import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import com.gard.investmentmanager.institution.infrastructure.persistence.InstitutionEntity;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AccountPersistenceAdapter implements AccountPersistencePort {

    private final AccountPanacheRepository accountPanacheRepository;
    private final AccountPersistenceMapper accountPersistenceMapper;
    private final EntityManager entityManager;

    public AccountPersistenceAdapter(
            AccountPanacheRepository accountPanacheRepository,
            AccountPersistenceMapper accountPersistenceMapper,
            EntityManager entityManager
    ) {
        this.accountPanacheRepository = accountPanacheRepository;
        this.accountPersistenceMapper = accountPersistenceMapper;
        this.entityManager = entityManager;
    }

    @Override
    public List<InvestmentAccount> findAllOrderedByName() {
        return accountPersistenceMapper.toDomainList(accountPanacheRepository.listAllOrderedByName());
    }

    @Override
    public Optional<InvestmentAccount> findById(Long accountId) {
        return accountPanacheRepository.findByIdOptional(accountId)
                .map(accountPersistenceMapper::toDomain);
    }

    @Override
    public InvestmentAccount save(InvestmentAccount account) {
        InvestmentAccountEntity entity;

        if (account.id() == null) {
            entity = new InvestmentAccountEntity();
        } else {
            entity = accountPanacheRepository.findByIdOptional(account.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + account.id()));
        }

        entity.user = entityManager.getReference(UserEntity.class, account.userId());
        entity.institution = entityManager.getReference(InstitutionEntity.class, account.institutionId());
        entity.name = account.name();
        entity.accountType = account.accountType();
        entity.baseCurrency = account.baseCurrency();
        entity.notes = account.notes();
        entity.createdAt = account.createdAt();
        entity.updatedAt = account.updatedAt();

        if (entity.id == null) {
            accountPanacheRepository.persist(entity);
        }

        entityManager.flush();
        return accountPersistenceMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Long accountId) {
        accountPanacheRepository.deleteById(accountId);
    }
}
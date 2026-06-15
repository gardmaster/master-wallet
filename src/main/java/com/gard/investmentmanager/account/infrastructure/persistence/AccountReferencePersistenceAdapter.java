package com.gard.investmentmanager.account.infrastructure.persistence;

import com.gard.investmentmanager.account.application.port.out.LoadAccountReferencePort;
import com.gard.investmentmanager.institution.infrastructure.persistence.InstitutionEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class AccountReferencePersistenceAdapter implements LoadAccountReferencePort {

    private final EntityManager entityManager;

    public AccountReferencePersistenceAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean institutionBelongsToUser(Long institutionId, Long userId) {
        Long count = entityManager.createQuery("""
                select count(i)
                from InstitutionEntity i
                where i.id = :institutionId
                  and i.user.id = :userId
                """, Long.class)
                .setParameter("institutionId", institutionId)
                .setParameter("userId", userId)
                .getSingleResult();

        return count != null && count > 0;
    }
}
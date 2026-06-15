package com.gard.investmentmanager.auth.infrastructure.persistence;

import com.gard.investmentmanager.shared.application.port.out.LoadUserPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class UserPersistenceAdapter implements LoadUserPort {

    private final EntityManager entityManager;

    public UserPersistenceAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean existsById(Long userId) {
        return entityManager.find(UserEntity.class, userId) != null;
    }
}
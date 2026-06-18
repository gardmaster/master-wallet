package com.gard.investmentmanager.auth.infrastructure.persistence;

import com.gard.investmentmanager.auth.application.port.out.UserPersistencePort;
import com.gard.investmentmanager.auth.domain.User;
import com.gard.investmentmanager.shared.application.port.out.LoadCurrentUserPort;
import com.gard.investmentmanager.shared.application.port.out.LoadUserPort;
import com.gard.investmentmanager.shared.domain.CurrentUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.Optional;

@ApplicationScoped
public class UserPersistenceAdapter implements UserPersistencePort, LoadCurrentUserPort, LoadUserPort {

    private final EntityManager entityManager;
    private final UserPersistenceMapper userPersistenceMapper;

    public UserPersistenceAdapter(EntityManager entityManager, UserPersistenceMapper userPersistenceMapper) {
        this.entityManager = entityManager;
        this.userPersistenceMapper = userPersistenceMapper;
    }

    @Override
    public boolean existsByEmail(String email) {
        Long count = entityManager.createQuery("""
                select count(u)
                from UserEntity u
                where lower(u.email) = lower(:email)
                """, Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count != null && count > 0;
    }

    @Override
    public boolean existsById(Long userId) {
        Long count = entityManager.createQuery("""
                select count(u)
                from UserEntity u
                where u.id = :userId
                """, Long.class)
                .setParameter("userId", userId)
                .getSingleResult();

        return count != null && count > 0;
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.name = user.name();
        entity.email = user.email();
        entity.externalSubject = user.externalSubject();
        entity.createdAt = user.createdAt();
        entity.updatedAt = user.updatedAt();

        entityManager.persist(entity);
        entityManager.flush();

        return userPersistenceMapper.toDomain(entity);
    }

    @Override
    public Optional<CurrentUser> findCurrentUserById(Long userId) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, userId))
                .map(userPersistenceMapper::toCurrentUser);
    }

    @Override
    public Optional<CurrentUser> findCurrentUserByExternalSubject(String externalSubject) {
        return entityManager.createQuery("""
                select u
                from UserEntity u
                where u.externalSubject = :externalSubject
                """, UserEntity.class)
                .setParameter("externalSubject", externalSubject)
                .getResultStream()
                .findFirst()
                .map(userPersistenceMapper::toCurrentUser);
    }
}
package com.gard.investmentmanager.institution.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InstitutionPanacheRepository implements PanacheRepositoryBase<InstitutionEntity, Long> {

    public List<InstitutionEntity> listAllOrderedByUserIdAndName(Long userId) {
        return find("user.id = ?1 and deletedAt is null order by name asc", userId).list();
    }

    public Optional<InstitutionEntity> findActiveByIdAndUserIdOptional(Long institutionId, Long userId) {
        return find("id = ?1 and user.id = ?2 and deletedAt is null", institutionId, userId)
                .firstResultOptional();
    }

    public void softDeleteById(Long institutionId, Long userId, Instant deletedAt) {
        update(
                "deletedAt = ?1 where id = ?2 and user.id = ?3 and deletedAt is null",
                deletedAt,
                institutionId,
                userId
        );
    }
}
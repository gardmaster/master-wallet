package com.gard.investmentmanager.institution.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InstitutionPanacheRepository implements PanacheRepositoryBase<InstitutionEntity, Long> {

    public List<InstitutionEntity> listAllOrderedByName() {
        return find("deletedAt is null order by name asc").list();
    }

    public Optional<InstitutionEntity> findActiveByIdOptional(Long institutionId) {
        return find("id = ?1 and deletedAt is null", institutionId).firstResultOptional();
    }

    public void softDeleteById(Long institutionId, Instant deletedAt) {
        update("deletedAt = ?1 where id = ?2 and deletedAt is null", deletedAt, institutionId);
    }
}
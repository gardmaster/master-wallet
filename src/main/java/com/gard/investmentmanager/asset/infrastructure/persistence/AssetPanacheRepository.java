package com.gard.investmentmanager.asset.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AssetPanacheRepository implements PanacheRepositoryBase<AssetEntity, Long> {

    public List<AssetEntity> listAllOrderedByName() {
        return find("deletedAt is null order by name asc").list();
    }

    public Optional<AssetEntity> findActiveByIdOptional(Long assetId) {
        return find("id = ?1 and deletedAt is null", assetId).firstResultOptional();
    }

    public void softDeleteById(Long assetId, Instant deletedAt) {
        update("deletedAt = ?1 where id = ?2 and deletedAt is null", deletedAt, assetId);
    }
}
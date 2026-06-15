package com.gard.investmentmanager.asset.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AssetPanacheRepository implements PanacheRepositoryBase<AssetEntity, Long> {

    public List<AssetEntity> listAllOrderedByName() {
        return find("order by name asc").list();
    }
}
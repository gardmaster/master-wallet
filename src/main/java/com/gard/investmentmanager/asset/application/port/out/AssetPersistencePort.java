package com.gard.investmentmanager.asset.application.port.out;

import com.gard.investmentmanager.asset.domain.Asset;

import java.util.List;
import java.util.Optional;

public interface AssetPersistencePort {

    List<Asset> findAllOrderedByName();

    Optional<Asset> findById(Long assetId);

    Asset save(Asset asset);

    void deleteById(Long assetId);
}
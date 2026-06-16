package com.gard.investmentmanager.asset.application.port.out;

import com.gard.investmentmanager.asset.domain.Asset;

import java.util.List;
import java.util.Optional;

public interface AssetPersistencePort {

    List<Asset> findAllOrderedByUserIdAndName(Long userId);

    Optional<Asset> findByIdAndUserId(Long assetId, Long userId);

    Asset save(Asset asset);

    void softDeleteById(Long assetId, Long userId);
}
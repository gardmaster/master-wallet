package com.gard.investmentmanager.asset.application.service;

import com.gard.investmentmanager.asset.application.port.in.GetAssetByIdUC;
import com.gard.investmentmanager.asset.application.port.out.AssetPersistencePort;
import com.gard.investmentmanager.asset.domain.Asset;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetAssetByIdService implements GetAssetByIdUC {

    private final AssetPersistencePort assetPersistencePort;

    public GetAssetByIdService(AssetPersistencePort assetPersistencePort) {
        this.assetPersistencePort = assetPersistencePort;
    }

    @Override
    public Asset execute(Long assetId) {
        return assetPersistencePort.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + assetId));
    }
}
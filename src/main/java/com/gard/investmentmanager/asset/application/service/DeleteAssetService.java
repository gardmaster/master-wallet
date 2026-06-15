package com.gard.investmentmanager.asset.application.service;

import com.gard.investmentmanager.asset.application.port.in.DeleteAssetUC;
import com.gard.investmentmanager.asset.application.port.out.AssetPersistencePort;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteAssetService implements DeleteAssetUC {

    private final AssetPersistencePort assetPersistencePort;

    public DeleteAssetService(AssetPersistencePort assetPersistencePort) {
        this.assetPersistencePort = assetPersistencePort;
    }

    @Override
    @Transactional
    public void execute(Long assetId) {
        if (assetPersistencePort.findById(assetId).isEmpty()) {
            throw new ResourceNotFoundException("Asset not found: " + assetId);
        }

        assetPersistencePort.deleteById(assetId);
    }
}
package com.gard.investmentmanager.asset.application.service;

import com.gard.investmentmanager.asset.application.port.in.ListAssetsUC;
import com.gard.investmentmanager.asset.application.port.out.AssetPersistencePort;
import com.gard.investmentmanager.asset.domain.Asset;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListAssetsService implements ListAssetsUC {

    private final AssetPersistencePort assetPersistencePort;

    public ListAssetsService(AssetPersistencePort assetPersistencePort) {
        this.assetPersistencePort = assetPersistencePort;
    }

    @Override
    public List<Asset> execute(Long currentUserId) {
        return assetPersistencePort.findAllOrderedByUserIdAndName(currentUserId);
    }
}
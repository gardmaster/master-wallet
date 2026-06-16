package com.gard.investmentmanager.asset.application.service;

import com.gard.investmentmanager.asset.application.port.in.GetAssetByIdUC;
import com.gard.investmentmanager.asset.application.port.out.AssetPersistencePort;
import com.gard.investmentmanager.asset.domain.Asset;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetAssetByIdService implements GetAssetByIdUC {

    private final AssetPersistencePort assetPersistencePort;
    private final MessageResolver messageResolver;

    public GetAssetByIdService(
            AssetPersistencePort assetPersistencePort,
            MessageResolver messageResolver
    ) {
        this.assetPersistencePort = assetPersistencePort;
        this.messageResolver = messageResolver;
    }

    @Override
    public Asset execute(Long currentUserId, Long assetId) {
        return assetPersistencePort.findByIdAndUserId(assetId, currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageResolver.get("error.asset.not-found", assetId)
                ));
    }
}
package com.gard.investmentmanager.asset.application.service;

import com.gard.investmentmanager.asset.application.port.in.DeleteAssetUC;
import com.gard.investmentmanager.asset.application.port.out.AssetPersistencePort;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteAssetService implements DeleteAssetUC {

    private final AssetPersistencePort assetPersistencePort;
    private final MessageResolver messageResolver;

    public DeleteAssetService(
            AssetPersistencePort assetPersistencePort,
            MessageResolver messageResolver
    ) {
        this.assetPersistencePort = assetPersistencePort;
        this.messageResolver = messageResolver;
    }

    @Override
    @Transactional
    public void execute(Long assetId) {
        if (assetPersistencePort.findById(assetId).isEmpty()) {
            throw new ResourceNotFoundException(
                    messageResolver.get("error.asset.not-found", assetId)
            );
        }

        assetPersistencePort.softDeleteById(assetId);
    }
}
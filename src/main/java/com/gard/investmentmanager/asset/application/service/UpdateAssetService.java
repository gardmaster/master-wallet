package com.gard.investmentmanager.asset.application.service;

import com.gard.investmentmanager.asset.application.port.in.UpdateAssetCommand;
import com.gard.investmentmanager.asset.application.port.in.UpdateAssetUC;
import com.gard.investmentmanager.asset.application.port.out.AssetPersistencePort;
import com.gard.investmentmanager.asset.domain.Asset;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class UpdateAssetService implements UpdateAssetUC {

    private final AssetPersistencePort assetPersistencePort;
    private final MessageResolver messageResolver;

    public UpdateAssetService(
            AssetPersistencePort assetPersistencePort,
            MessageResolver messageResolver
    ) {
        this.assetPersistencePort = assetPersistencePort;
        this.messageResolver = messageResolver;
    }

    @Override
    @Transactional
    public Asset execute(Long assetId, UpdateAssetCommand command) {
        Asset current = assetPersistencePort.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageResolver.get("error.asset.not-found", assetId)
                ));

        Asset updated = new Asset(
                current.id(),
                current.userId(),
                command.assetType(),
                command.name().trim(),
                normalizeNullable(command.ticker()),
                normalizeUpperNullable(command.country()),
                command.currency().trim().toUpperCase(),
                normalizeNullable(command.issuer()),
                normalizeNullable(command.metadataJson()),
                current.createdAt(),
                Instant.now()
        );

        return assetPersistencePort.save(updated);
    }

    private String normalizeNullable(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String normalizeUpperNullable(String value) {
        String normalized = normalizeNullable(value);
        return normalized == null ? null : normalized.toUpperCase();
    }
}
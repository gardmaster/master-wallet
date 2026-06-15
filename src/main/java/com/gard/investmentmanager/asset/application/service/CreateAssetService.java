package com.gard.investmentmanager.asset.application.service;

import com.gard.investmentmanager.asset.application.port.in.CreateAssetCommand;
import com.gard.investmentmanager.asset.application.port.in.CreateAssetUC;
import com.gard.investmentmanager.asset.application.port.out.AssetPersistencePort;
import com.gard.investmentmanager.asset.domain.Asset;
import com.gard.investmentmanager.shared.application.port.out.LoadUserPort;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class CreateAssetService implements CreateAssetUC {

    private final AssetPersistencePort assetPersistencePort;
    private final LoadUserPort loadUserPort;
    private final MessageResolver messageResolver;

    public CreateAssetService(
            AssetPersistencePort assetPersistencePort,
            LoadUserPort loadUserPort,
            MessageResolver messageResolver
    ) {
        this.assetPersistencePort = assetPersistencePort;
        this.loadUserPort = loadUserPort;
        this.messageResolver = messageResolver;
    }

    @Override
    @Transactional
    public Asset execute(CreateAssetCommand command) {
        if (!loadUserPort.existsById(command.userId())) {
            throw new ResourceNotFoundException(
                    messageResolver.get("error.user.not-found", command.userId())
            );
        }

        Instant now = Instant.now();

        Asset asset = new Asset(
                null,
                command.userId(),
                command.assetType(),
                command.name().trim(),
                normalizeNullable(command.ticker()),
                normalizeUpperNullable(command.country()),
                command.currency().trim().toUpperCase(),
                normalizeNullable(command.issuer()),
                normalizeNullable(command.metadataJson()),
                now,
                now
        );

        return assetPersistencePort.save(asset);
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
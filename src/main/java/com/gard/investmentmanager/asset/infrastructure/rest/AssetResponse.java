package com.gard.investmentmanager.asset.infrastructure.rest;

import com.gard.investmentmanager.asset.domain.AssetType;

import java.time.Instant;

public record AssetResponse(
        Long id,
        Long userId,
        AssetType assetType,
        String name,
        String ticker,
        String country,
        String currency,
        String issuer,
        String metadataJson,
        Instant createdAt,
        Instant updatedAt
) {
}
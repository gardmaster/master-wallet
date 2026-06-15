package com.gard.investmentmanager.asset.domain;

import java.time.Instant;

public record Asset(
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
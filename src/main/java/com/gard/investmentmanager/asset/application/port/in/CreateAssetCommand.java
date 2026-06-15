package com.gard.investmentmanager.asset.application.port.in;

import com.gard.investmentmanager.asset.domain.AssetType;

public record CreateAssetCommand(
        Long userId,
        AssetType assetType,
        String name,
        String ticker,
        String country,
        String currency,
        String issuer,
        String metadataJson
) {
}
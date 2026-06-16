package com.gard.investmentmanager.asset.application.port.in;

import com.gard.investmentmanager.asset.domain.AssetType;

public record CreateAssetCommand(
        AssetType assetType,
        String name,
        String ticker,
        String country,
        String currency,
        String issuer,
        String metadataJson
) {
}
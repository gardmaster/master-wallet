package com.gard.investmentmanager.asset.infrastructure.rest;

import com.gard.investmentmanager.asset.domain.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateAssetRequest(
        @NotNull AssetType assetType,
        @NotBlank @Size(max = 200) String name,
        @Size(max = 30) String ticker,
        @Pattern(regexp = "^[A-Za-z]{2}$") String country,
        @NotBlank @Pattern(regexp = "^[A-Za-z]{3}$") String currency,
        @Size(max = 150) String issuer,
        String metadataJson
) {
}
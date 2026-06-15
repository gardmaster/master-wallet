package com.gard.investmentmanager.asset.infrastructure.rest;

import com.gard.investmentmanager.asset.domain.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateAssetRequest(
        @NotNull(message = "{validation.not-null}")
        AssetType assetType,

        @NotBlank(message = "{validation.not-blank}")
        @Size(max = 200, message = "{validation.size.max}")
        String name,

        @Size(max = 30, message = "{validation.size.max}")
        String ticker,

        @Pattern(regexp = "^[A-Za-z]{2}$", message = "{validation.country-code}")
        String country,

        @NotBlank(message = "{validation.not-blank}")
        @Pattern(regexp = "^[A-Za-z]{3}$", message = "{validation.currency-code}")
        String currency,

        @Size(max = 150, message = "{validation.size.max}")
        String issuer,

        String metadataJson
) {
}
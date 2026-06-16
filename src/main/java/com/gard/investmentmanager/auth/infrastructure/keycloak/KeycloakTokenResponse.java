package com.gard.investmentmanager.auth.infrastructure.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KeycloakTokenResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
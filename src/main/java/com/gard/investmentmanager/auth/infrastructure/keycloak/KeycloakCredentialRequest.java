package com.gard.investmentmanager.auth.infrastructure.keycloak;

public record KeycloakCredentialRequest(
        String type,
        String value,
        boolean temporary
) {
}
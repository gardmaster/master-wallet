package com.gard.investmentmanager.auth.infrastructure.keycloak;

import java.util.List;

public record KeycloakCreateUserRequest(
        String username,
        String email,
        boolean enabled,
        boolean emailVerified,
        List<KeycloakCredentialRequest> credentials
) {
}
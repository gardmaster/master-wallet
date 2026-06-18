package com.gard.investmentmanager.auth.infrastructure.keycloak;

import java.util.List;

public record KeycloakCreateUserRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        boolean emailVerified,
        List<KeycloakCredentialRequest> credentials
) {
}
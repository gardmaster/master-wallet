package com.gard.investmentmanager.auth.infrastructure.keycloak;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gard.investmentmanager.auth.application.port.in.RegisterUserCommand;
import com.gard.investmentmanager.auth.application.port.out.IdentityProvisioningPort;
import com.gard.investmentmanager.shared.domain.BusinessException;
import com.gard.investmentmanager.shared.domain.IdentityProviderException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ApplicationScoped
public class KeycloakIdentityProvisioningAdapter implements IdentityProvisioningPort {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final MessageResolver messageResolver;
    private final String keycloakServerUrl;
    private final String keycloakRealm;
    private final String adminClientId;
    private final String adminClientSecret;

    public KeycloakIdentityProvisioningAdapter(
            ObjectMapper objectMapper,
            MessageResolver messageResolver,
            @ConfigProperty(name = "app.keycloak.admin.server-url") String keycloakServerUrl,
            @ConfigProperty(name = "app.keycloak.admin.realm") String keycloakRealm,
            @ConfigProperty(name = "app.keycloak.admin.client-id") String adminClientId,
            @ConfigProperty(name = "app.keycloak.admin.client-secret") String adminClientSecret
    ) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
        this.messageResolver = messageResolver;
        this.keycloakServerUrl = keycloakServerUrl;
        this.keycloakRealm = keycloakRealm;
        this.adminClientId = adminClientId;
        this.adminClientSecret = adminClientSecret;
    }

    @Override
    public String createUser(RegisterUserCommand command) {
        String accessToken = requestAdminAccessToken();

        KeycloakCreateUserRequest requestBody = new KeycloakCreateUserRequest(
                command.email().trim().toLowerCase(),
                command.email().trim().toLowerCase(),
                command.firstName().trim(),
                command.lastName().trim(),
                true,
                true,
                List.of(
                        new KeycloakCredentialRequest(
                                "password",
                                command.password(),
                                false
                        )
                )
        );

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(keycloakServerUrl + "/admin/realms/" + keycloakRealm + "/users"))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                return extractUserIdFromLocation(response.headers().firstValue("Location").orElse(null));
            }

            if (response.statusCode() == 409) {
                throw new BusinessException(
                        messageResolver.get("error.user.email-already-exists", command.email())
                );
            }

            throw new IdentityProviderException(
                    messageResolver.get("error.keycloak.user-creation-failed")
            );
        } catch (IOException exception) {
            throw new IdentityProviderException(
                    messageResolver.get("error.keycloak.user-creation-failed")
            );
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IdentityProviderException(
                    messageResolver.get("error.keycloak.user-creation-failed")
            );
        }
    }

    @Override
    public void deleteUserByExternalSubject(String externalSubject) {
        String accessToken = requestAdminAccessToken();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(keycloakServerUrl + "/admin/realms/" + keycloakRealm + "/users/" + externalSubject))
                    .header("Authorization", "Bearer " + accessToken)
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 204 && response.statusCode() != 404) {
                throw new IdentityProviderException(
                        messageResolver.get("error.keycloak.user-deletion-failed", externalSubject)
                );
            }
        } catch (IOException exception) {
            throw new IdentityProviderException(
                    messageResolver.get("error.keycloak.user-deletion-failed", externalSubject)
            );
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IdentityProviderException(
                    messageResolver.get("error.keycloak.user-deletion-failed", externalSubject)
            );
        }
    }

    private String requestAdminAccessToken() {
        String form = "grant_type=client_credentials"
                + "&client_id=" + encode(adminClientId)
                + "&client_secret=" + encode(adminClientSecret);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(keycloakServerUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new IdentityProviderException(
                        messageResolver.get("error.keycloak.admin-token-failed")
                );
            }

            KeycloakTokenResponse tokenResponse = objectMapper.readValue(response.body(), KeycloakTokenResponse.class);
            return tokenResponse.accessToken();
        } catch (IOException exception) {
            throw new IdentityProviderException(
                    messageResolver.get("error.keycloak.admin-token-failed")
            );
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IdentityProviderException(
                    messageResolver.get("error.keycloak.admin-token-failed")
            );
        }
    }

    private String extractUserIdFromLocation(String location) {
        if (location == null || location.isBlank()) {
            throw new IdentityProviderException(
                    messageResolver.get("error.keycloak.user-creation-failed")
            );
        }

        int lastSlash = location.lastIndexOf('/');
        if (lastSlash < 0 || lastSlash == location.length() - 1) {
            throw new IdentityProviderException(
                    messageResolver.get("error.keycloak.user-creation-failed")
            );
        }

        return location.substring(lastSlash + 1);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
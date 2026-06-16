package com.gard.investmentmanager.shared.infrastructure.security;

import com.gard.investmentmanager.shared.application.port.out.LoadCurrentUserPort;
import com.gard.investmentmanager.shared.domain.CurrentUser;
import com.gard.investmentmanager.shared.domain.UnauthenticatedException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import com.gard.investmentmanager.shared.infrastructure.rest.RequestHeaderNames;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

@RequestScoped
public class HeaderCurrentUserProvider {

    @Context
    HttpHeaders httpHeaders;

    private final LoadCurrentUserPort loadCurrentUserPort;
    private final MessageResolver messageResolver;

    public HeaderCurrentUserProvider(
            LoadCurrentUserPort loadCurrentUserPort,
            MessageResolver messageResolver
    ) {
        this.loadCurrentUserPort = loadCurrentUserPort;
        this.messageResolver = messageResolver;
    }

    public CurrentUser getCurrentUser() {
        String rawUserId = httpHeaders.getHeaderString(RequestHeaderNames.X_USER_ID);

        if (rawUserId == null || rawUserId.isBlank()) {
            throw new UnauthenticatedException(
                    messageResolver.get("error.current-user-id.missing", RequestHeaderNames.X_USER_ID)
            );
        }

        try {
            long userId = Long.parseLong(rawUserId.trim());

            if (userId <= 0) {
                throw new UnauthenticatedException(
                        messageResolver.get("error.current-user-id.invalid", rawUserId)
                );
            }

            return loadCurrentUserPort.findCurrentUserById(userId)
                    .orElseThrow(() -> new UnauthenticatedException(
                            messageResolver.get("error.current-user-id.invalid", rawUserId)
                    ));
        } catch (NumberFormatException exception) {
            throw new UnauthenticatedException(
                    messageResolver.get("error.current-user-id.invalid", rawUserId)
            );
        }
    }
}
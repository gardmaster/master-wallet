package com.gard.investmentmanager.shared.infrastructure.security;

import com.gard.investmentmanager.shared.application.port.in.CurrentUserProvider;
import com.gard.investmentmanager.shared.domain.CurrentUser;
import com.gard.investmentmanager.shared.domain.UnauthenticatedException;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Alternative;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@RequestScoped
@Alternative
@Priority(1)
public class CompositeCurrentUserProvider implements CurrentUserProvider {

    private final TokenCurrentUserProvider tokenCurrentUserProvider;
    private final HeaderCurrentUserProvider headerCurrentUserProvider;
    private final boolean headerFallbackEnabled;

    public CompositeCurrentUserProvider(
            TokenCurrentUserProvider tokenCurrentUserProvider,
            HeaderCurrentUserProvider headerCurrentUserProvider,
            @ConfigProperty(name = "app.security.current-user.header-fallback-enabled") boolean headerFallbackEnabled
    ) {
        this.tokenCurrentUserProvider = tokenCurrentUserProvider;
        this.headerCurrentUserProvider = headerCurrentUserProvider;
        this.headerFallbackEnabled = headerFallbackEnabled;
    }

    @Override
    public CurrentUser getCurrentUser() {
        try {
            return tokenCurrentUserProvider.getCurrentUser();
        } catch (UnauthenticatedException exception) {
            if (!headerFallbackEnabled) {
                throw exception;
            }

            return headerCurrentUserProvider.getCurrentUser();
        }
    }
}
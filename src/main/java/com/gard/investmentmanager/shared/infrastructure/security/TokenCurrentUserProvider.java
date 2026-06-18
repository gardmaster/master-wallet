package com.gard.investmentmanager.shared.infrastructure.security;

import com.gard.investmentmanager.shared.application.port.out.LoadCurrentUserPort;
import com.gard.investmentmanager.shared.domain.CurrentUser;
import com.gard.investmentmanager.shared.domain.UnauthenticatedException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class TokenCurrentUserProvider {

    private final SecurityIdentity securityIdentity;
    private final LoadCurrentUserPort loadCurrentUserPort;
    private final MessageResolver messageResolver;

    public TokenCurrentUserProvider(
            SecurityIdentity securityIdentity,
            LoadCurrentUserPort loadCurrentUserPort,
            MessageResolver messageResolver
    ) {
        this.securityIdentity = securityIdentity;
        this.loadCurrentUserPort = loadCurrentUserPort;
        this.messageResolver = messageResolver;
    }

    public CurrentUser getCurrentUser() {
        if (securityIdentity == null || securityIdentity.isAnonymous()) {
            throw new UnauthenticatedException(
                    messageResolver.get("error.current-user.unauthenticated")
            );
        }

        String subject = securityIdentity.getPrincipal().getName();

        if (subject == null || subject.isBlank()) {
            throw new UnauthenticatedException(
                    messageResolver.get("error.current-user.missing-claim", "sub")
            );
        }

        return loadCurrentUserPort.findCurrentUserByExternalSubject(subject)
                .orElseThrow(() -> new UnauthenticatedException(
                        messageResolver.get("error.current-user.not-linked", subject)
                ));
    }
}
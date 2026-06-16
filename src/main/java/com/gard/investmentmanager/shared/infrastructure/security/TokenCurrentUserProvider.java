package com.gard.investmentmanager.shared.infrastructure.security;

import com.gard.investmentmanager.shared.application.port.in.CurrentUserProvider;
import com.gard.investmentmanager.shared.domain.CurrentUser;
import com.gard.investmentmanager.shared.domain.UnauthenticatedException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@RequestScoped
public class TokenCurrentUserProvider implements CurrentUserProvider {

    private final SecurityIdentity securityIdentity;
    private final MessageResolver messageResolver;
    private final String userIdClaim;
    private final String subjectClaim;

    public TokenCurrentUserProvider(
            SecurityIdentity securityIdentity,
            MessageResolver messageResolver,
            @ConfigProperty(name = "app.security.current-user.claim") String userIdClaim,
            @ConfigProperty(name = "app.security.current-user.subject-claim") String subjectClaim
    ) {
        this.securityIdentity = securityIdentity;
        this.messageResolver = messageResolver;
        this.userIdClaim = userIdClaim;
        this.subjectClaim = subjectClaim;
    }

    @Override
    public CurrentUser getCurrentUser() {
        if (securityIdentity == null || securityIdentity.isAnonymous()) {
            throw new UnauthenticatedException(
                    messageResolver.get("error.current-user.unauthenticated")
            );
        }

        Object claimValue = securityIdentity.getAttribute(userIdClaim);

        if (claimValue == null) {
            claimValue = securityIdentity.getAttribute(subjectClaim);
        }

        if (claimValue == null) {
            throw new UnauthenticatedException(
                    messageResolver.get("error.current-user.missing-claim", userIdClaim)
            );
        }

        return new CurrentUser(parseUserId(claimValue));
    }

    private Long parseUserId(Object claimValue) {
        if (claimValue instanceof Number number) {
            long userId = number.longValue();

            if (userId <= 0) {
                throw new UnauthenticatedException(
                        messageResolver.get("error.current-user.invalid-claim", claimValue)
                );
            }

            return userId;
        }

        String rawValue = String.valueOf(claimValue).trim();

        try {
            long userId = Long.parseLong(rawValue);

            if (userId <= 0) {
                throw new UnauthenticatedException(
                        messageResolver.get("error.current-user.invalid-claim", rawValue)
                );
            }

            return userId;
        } catch (NumberFormatException exception) {
            throw new UnauthenticatedException(
                    messageResolver.get("error.current-user.invalid-claim", rawValue)
            );
        }
    }
}
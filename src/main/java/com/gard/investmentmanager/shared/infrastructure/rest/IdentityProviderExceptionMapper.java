package com.gard.investmentmanager.shared.infrastructure.rest;

import com.gard.investmentmanager.shared.domain.IdentityProviderException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class IdentityProviderExceptionMapper extends BaseProblemDetailsExceptionMapper<IdentityProviderException> {

    private final MessageResolver messageResolver;

    public IdentityProviderExceptionMapper(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @Override
    public Response toResponse(IdentityProviderException exception) {
        return buildResponse(
                Response.Status.BAD_GATEWAY,
                "TO BE IMPLEMENTED",
                messageResolver.get("problem.title.identity-provider"),
                exception.getMessage()
        );
    }
}
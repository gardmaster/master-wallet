package com.gard.investmentmanager.shared.infrastructure.rest;

import com.gard.investmentmanager.shared.domain.UnauthenticatedException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class UnauthenticatedExceptionMapper extends BaseProblemDetailsExceptionMapper<UnauthenticatedException> {

    private final MessageResolver messageResolver;

    public UnauthenticatedExceptionMapper(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @Override
    public Response toResponse(UnauthenticatedException exception) {
        return buildResponse(
                Response.Status.UNAUTHORIZED,
                "TO BE IMPLEMENTED",
                messageResolver.get("problem.title.unauthenticated"),
                exception.getMessage()
        );
    }
}
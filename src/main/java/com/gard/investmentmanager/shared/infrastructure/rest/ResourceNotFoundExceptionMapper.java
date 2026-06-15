package com.gard.investmentmanager.shared.infrastructure.rest;

import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ResourceNotFoundExceptionMapper extends BaseProblemDetailsExceptionMapper<ResourceNotFoundException> {

    @Inject
    MessageResolver messageResolver;

    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        return buildResponse(
                Response.Status.NOT_FOUND,
                "TO BE IMPLEMENTED",
                messageResolver.get("problem.title.not-found"),
                exception.getMessage()
        );
    }
}
package com.gard.investmentmanager.shared.infrastructure.rest;

import com.gard.investmentmanager.shared.domain.BusinessException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class BusinessExceptionMapper extends BaseProblemDetailsExceptionMapper<BusinessException> {

    private final MessageResolver messageResolver;

    public BusinessExceptionMapper(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @Override
    public Response toResponse(BusinessException exception) {
        return buildResponse(
                Response.Status.BAD_REQUEST,
                "TO BE IMPLEMENTED",
                messageResolver.get("problem.title.business"),
                exception.getMessage()
        );
    }
}
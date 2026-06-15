package com.gard.investmentmanager.shared.infrastructure.rest;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.List;

public abstract class BaseProblemDetailsExceptionMapper<T extends Throwable> implements ExceptionMapper<T> {

    private static final String PROBLEM_DETAILS_MEDIA_TYPE = "application/problem+json";

    @Context
    UriInfo uriInfo;

    protected Response buildResponse(
            Response.Status status,
            String type,
            String title,
            String detail
    ) {
        return buildResponse(status, type, title, detail, null);
    }

    protected Response buildResponse(
            Response.Status status,
            String type,
            String title,
            String detail,
            List<ProblemDetailsViolation> violations
    ) {
        ProblemDetailsResponse body = new ProblemDetailsResponse(
                type,
                title,
                status.getStatusCode(),
                detail,
                uriInfo == null ? null : uriInfo.getRequestUri().toString(),
                violations
        );

        return Response.status(status)
                .type(PROBLEM_DETAILS_MEDIA_TYPE)
                .entity(body)
                .build();
    }
}
package com.gard.investmentmanager.shared.infrastructure.rest;

import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.Comparator;
import java.util.List;

@Provider
@ApplicationScoped
public class ConstraintViolationExceptionMapper extends BaseProblemDetailsExceptionMapper<ConstraintViolationException> {

    private final MessageResolver messageResolver;

    public ConstraintViolationExceptionMapper(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ProblemDetailsViolation> violations = exception.getConstraintViolations()
                .stream()
                .sorted(Comparator.comparing(v -> v.getPropertyPath().toString()))
                .map(this::toViolation)
                .toList();

        return buildResponse(
                Response.Status.BAD_REQUEST,
                "TO BE IMPLEMENTED",
                messageResolver.get("problem.title.validation"),
                messageResolver.get("problem.detail.validation"),
                violations
        );
    }

    private ProblemDetailsViolation toViolation(ConstraintViolation<?> violation) {
        return new ProblemDetailsViolation(
                violation.getPropertyPath().toString(),
                violation.getMessage()
        );
    }
}
package com.gard.investmentmanager.auth.infrastructure.rest;

public record CurrentUserResponse(
        Long id,
        String name,
        String email
) {
}
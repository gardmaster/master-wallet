package com.gard.investmentmanager.shared.domain;

public record CurrentUser(
        Long id,
        String name,
        String email,
        String externalSubject
) {
}
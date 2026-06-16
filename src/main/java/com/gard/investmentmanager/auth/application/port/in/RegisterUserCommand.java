package com.gard.investmentmanager.auth.application.port.in;

public record RegisterUserCommand(
        String name,
        String email,
        String password
) {
}
package com.gard.investmentmanager.auth.application.port.in;

public record RegisterUserCommand(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
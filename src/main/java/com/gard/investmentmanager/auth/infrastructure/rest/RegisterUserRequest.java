package com.gard.investmentmanager.auth.infrastructure.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank(message = "{validation.not-blank}")
        @Size(max = 100, message = "{validation.size.max}")
        String firstName,

        @NotBlank(message = "{validation.not-blank}")
        @Size(max = 100, message = "{validation.size.max}")
        String lastName,

        @NotBlank(message = "{validation.not-blank}")
        @Email
        @Size(max = 255, message = "{validation.size.max}")
        String email,

        @NotBlank(message = "{validation.not-blank}")
        @Size(min = 8, max = 100, message = "{validation.size.max}")
        String password
) {
}
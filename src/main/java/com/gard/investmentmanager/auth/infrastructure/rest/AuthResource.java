package com.gard.investmentmanager.auth.infrastructure.rest;

import com.gard.investmentmanager.auth.application.port.in.RegisterUserCommand;
import com.gard.investmentmanager.auth.application.port.in.RegisterUserUC;
import com.gard.investmentmanager.auth.domain.User;
import com.gard.investmentmanager.shared.application.port.in.CurrentUserProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@ApplicationScoped
public class AuthResource implements AuthResourceContract {

    private final RegisterUserUC registerUserUC;
    private final CurrentUserProvider currentUserProvider;
    private final AuthRestMapper authRestMapper;

    public AuthResource(
            RegisterUserUC registerUserUC,
            CurrentUserProvider currentUserProvider,
            AuthRestMapper authRestMapper
    ) {
        this.registerUserUC = registerUserUC;
        this.currentUserProvider = currentUserProvider;
        this.authRestMapper = authRestMapper;
    }

    @Override
    public Response register(RegisterUserRequest request) {
        User created = registerUserUC.execute(
                new RegisterUserCommand(
                        request.name(),
                        request.email(),
                        request.password()
                )
        );

        UserResponse response = authRestMapper.toResponse(created);

        return Response.created(URI.create("/api/v1/auth/me"))
                .entity(response)
                .build();
    }

    @Override
    public CurrentUserResponse me() {
        return authRestMapper.toCurrentUserResponse(currentUserProvider.getCurrentUser());
    }
}
package com.gard.investmentmanager.auth.infrastructure.rest;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Auth")
public interface AuthResourceContract {

    @POST
    @Path("/register")
    @Operation(summary = "Register application user and Keycloak identity")
    Response register(@Valid RegisterUserRequest request);

    @GET
    @Path("/me")
    @Operation(summary = "Get current authenticated user")
    CurrentUserResponse me();
}
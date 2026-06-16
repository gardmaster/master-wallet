package com.gard.investmentmanager.account.infrastructure.rest;

import com.gard.investmentmanager.shared.infrastructure.rest.RequestHeaderNames;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/v1/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Accounts")
public interface AccountResourceContract {

    @POST
    @Operation(summary = "Create account")
    Response create(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId,
            @Valid CreateAccountRequest request
    );

    @GET
    @Operation(summary = "List all accounts from current user")
    List<AccountResponse> listAll(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId
    );

    @GET
    @Path("/{accountId}")
    @Operation(summary = "Get account by id from current user")
    AccountResponse getById(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId,
            @PathParam("accountId") Long accountId
    );

    @PUT
    @Path("/{accountId}")
    @Operation(summary = "Update account from current user")
    AccountResponse update(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId,
            @PathParam("accountId") Long accountId,
            @Valid UpdateAccountRequest request
    );

    @DELETE
    @Path("/{accountId}")
    @Operation(summary = "Delete account from current user")
    Response delete(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId,
            @PathParam("accountId") Long accountId
    );
}
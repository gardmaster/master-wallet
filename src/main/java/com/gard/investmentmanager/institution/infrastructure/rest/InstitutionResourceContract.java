package com.gard.investmentmanager.institution.infrastructure.rest;

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

@Path("/api/v1/institutions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Institutions")
public interface InstitutionResourceContract {

    @POST
    @Operation(summary = "Create institution")
    Response create(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId,
            @Valid CreateInstitutionRequest request
    );

    @GET
    @Operation(summary = "List all institutions from current user")
    List<InstitutionResponse> listAll(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId
    );

    @GET
    @Path("/{institutionId}")
    @Operation(summary = "Get institution by id from current user")
    InstitutionResponse getById(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId,
            @PathParam("institutionId") Long institutionId
    );

    @PUT
    @Path("/{institutionId}")
    @Operation(summary = "Update institution from current user")
    InstitutionResponse update(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId,
            @PathParam("institutionId") Long institutionId,
            @Valid UpdateInstitutionRequest request
    );

    @DELETE
    @Path("/{institutionId}")
    @Operation(summary = "Delete institution from current user")
    Response delete(
            @HeaderParam(RequestHeaderNames.X_USER_ID)
            @NotNull(message = "{validation.not-null}")
            Long currentUserId,
            @PathParam("institutionId") Long institutionId
    );
}
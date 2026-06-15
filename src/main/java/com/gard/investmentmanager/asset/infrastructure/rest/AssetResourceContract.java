package com.gard.investmentmanager.asset.infrastructure.rest;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
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

@Path("/api/v1/assets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Assets")
public interface AssetResourceContract {

    @POST
    @Operation(summary = "Create asset")
    Response create(@Valid CreateAssetRequest request);

    @GET
    @Operation(summary = "List all assets")
    List<AssetResponse> listAll();

    @GET
    @Path("/{assetId}")
    @Operation(summary = "Get asset by id")
    AssetResponse getById(@PathParam("assetId") Long assetId);

    @PUT
    @Path("/{assetId}")
    @Operation(summary = "Update asset")
    AssetResponse update(
            @PathParam("assetId") Long assetId,
            @Valid UpdateAssetRequest request
    );

    @DELETE
    @Path("/{assetId}")
    @Operation(summary = "Delete asset")
    Response delete(@PathParam("assetId") Long assetId);
}
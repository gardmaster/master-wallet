package com.gard.investmentmanager.institution.infrastructure.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/v1/institutions")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Institutions")
public interface InstitutionResourceContract {

    @GET
    @Operation(summary = "List all institutions")
    List<InstitutionResponse> listAll();
}
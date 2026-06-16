package com.gard.investmentmanager.institution.infrastructure.rest;

import com.gard.investmentmanager.institution.application.port.in.CreateInstitutionCommand;
import com.gard.investmentmanager.institution.application.port.in.CreateInstitutionUC;
import com.gard.investmentmanager.institution.application.port.in.DeleteInstitutionUC;
import com.gard.investmentmanager.institution.application.port.in.GetInstitutionByIdUC;
import com.gard.investmentmanager.institution.application.port.in.ListInstitutionsUC;
import com.gard.investmentmanager.institution.application.port.in.UpdateInstitutionCommand;
import com.gard.investmentmanager.institution.application.port.in.UpdateInstitutionUC;
import com.gard.investmentmanager.institution.domain.Institution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class InstitutionResource implements InstitutionResourceContract {

    private final CreateInstitutionUC createInstitutionUC;
    private final ListInstitutionsUC listInstitutionsUC;
    private final GetInstitutionByIdUC getInstitutionByIdUC;
    private final UpdateInstitutionUC updateInstitutionUC;
    private final DeleteInstitutionUC deleteInstitutionUC;
    private final InstitutionRestMapper institutionRestMapper;

    public InstitutionResource(
            CreateInstitutionUC createInstitutionUC,
            ListInstitutionsUC listInstitutionsUC,
            GetInstitutionByIdUC getInstitutionByIdUC,
            UpdateInstitutionUC updateInstitutionUC,
            DeleteInstitutionUC deleteInstitutionUC,
            InstitutionRestMapper institutionRestMapper
    ) {
        this.createInstitutionUC = createInstitutionUC;
        this.listInstitutionsUC = listInstitutionsUC;
        this.getInstitutionByIdUC = getInstitutionByIdUC;
        this.updateInstitutionUC = updateInstitutionUC;
        this.deleteInstitutionUC = deleteInstitutionUC;
        this.institutionRestMapper = institutionRestMapper;
    }

    @Override
    public Response create(Long currentUserId, CreateInstitutionRequest request) {
        Institution created = createInstitutionUC.execute(
                currentUserId,
                new CreateInstitutionCommand(
                        request.name(),
                        request.institutionType(),
                        request.notes()
                )
        );

        InstitutionResponse response = institutionRestMapper.toResponse(created);

        return Response.created(URI.create("/api/v1/institutions/" + response.id()))
                .entity(response)
                .build();
    }

    @Override
    public List<InstitutionResponse> listAll(Long currentUserId) {
        return institutionRestMapper.toResponseList(listInstitutionsUC.execute(currentUserId));
    }

    @Override
    public InstitutionResponse getById(Long currentUserId, Long institutionId) {
        return institutionRestMapper.toResponse(getInstitutionByIdUC.execute(currentUserId, institutionId));
    }

    @Override
    public InstitutionResponse update(Long currentUserId, Long institutionId, UpdateInstitutionRequest request) {
        return institutionRestMapper.toResponse(
                updateInstitutionUC.execute(
                        currentUserId,
                        institutionId,
                        new UpdateInstitutionCommand(
                                request.name(),
                                request.institutionType(),
                                request.notes()
                        )
                )
        );
    }

    @Override
    public Response delete(Long currentUserId, Long institutionId) {
        deleteInstitutionUC.execute(currentUserId, institutionId);
        return Response.noContent().build();
    }
}
package com.gard.investmentmanager.institution.infrastructure.rest;

import com.gard.investmentmanager.institution.application.port.in.ListInstitutionsUC;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class InstitutionResource implements InstitutionResourceContract {

    private final ListInstitutionsUC listInstitutionsUC;
    private final InstitutionRestMapper institutionRestMapper;

    public InstitutionResource(
            ListInstitutionsUC listInstitutionsUC,
            InstitutionRestMapper institutionRestMapper
    ) {
        this.listInstitutionsUC = listInstitutionsUC;
        this.institutionRestMapper = institutionRestMapper;
    }

    @Override
    public List<InstitutionResponse> listAll() {
        return institutionRestMapper.toResponseList(listInstitutionsUC.execute());
    }
}
package com.gard.investmentmanager.institution.application.service;

import com.gard.investmentmanager.institution.application.port.in.ListInstitutionsUC;
import com.gard.investmentmanager.institution.application.port.out.LoadInstitutionsPort;
import com.gard.investmentmanager.institution.domain.Institution;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListInstitutionsService implements ListInstitutionsUC {

    private final LoadInstitutionsPort loadInstitutionsPort;

    public ListInstitutionsService(LoadInstitutionsPort loadInstitutionsPort) {
        this.loadInstitutionsPort = loadInstitutionsPort;
    }

    @Override
    public List<Institution> execute() {
        return loadInstitutionsPort.loadAllOrderedByName();
    }
}

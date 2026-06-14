package com.gard.investmentmanager.institution.infrastructure.persistence;

import com.gard.investmentmanager.institution.application.port.out.LoadInstitutionsPort;
import com.gard.investmentmanager.institution.domain.Institution;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class InstitutionPersistenceAdapter implements LoadInstitutionsPort {

    private final InstitutionPanacheRepository institutionPanacheRepository;
    private final InstitutionPersistenceMapper institutionPersistenceMapper;

    public InstitutionPersistenceAdapter(
            InstitutionPanacheRepository institutionPanacheRepository,
            InstitutionPersistenceMapper institutionPersistenceMapper
    ) {
        this.institutionPanacheRepository = institutionPanacheRepository;
        this.institutionPersistenceMapper = institutionPersistenceMapper;
    }

    @Override
    public List<Institution> loadAllOrderedByName() {
        return institutionPersistenceMapper.toDomainList(
                institutionPanacheRepository.listAllOrderedByName()
        );
    }
}
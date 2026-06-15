package com.gard.investmentmanager.institution.application.port.out;

import com.gard.investmentmanager.institution.domain.Institution;

import java.util.List;
import java.util.Optional;

public interface InstitutionPersistencePort {

    List<Institution> findAllOrderedByName();

    Optional<Institution> findById(Long institutionId);

    Institution save(Institution institution);

    void deleteById(Long institutionId);
}
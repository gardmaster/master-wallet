package com.gard.investmentmanager.institution.application.port.out;

import com.gard.investmentmanager.institution.domain.Institution;

import java.util.List;
import java.util.Optional;

public interface InstitutionPersistencePort {

    List<Institution> findAllOrderedByUserIdAndName(Long userId);

    Optional<Institution> findByIdAndUserId(Long institutionId, Long userId);

    Institution save(Institution institution);

    void softDeleteById(Long institutionId, Long userId);
}
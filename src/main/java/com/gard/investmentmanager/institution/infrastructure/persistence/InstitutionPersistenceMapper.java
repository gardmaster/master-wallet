package com.gard.investmentmanager.institution.infrastructure.persistence;

import com.gard.investmentmanager.institution.domain.Institution;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface InstitutionPersistenceMapper {

    Institution toDomain(InstitutionEntity entity);

    List<Institution> toDomainList(List<InstitutionEntity> entities);
}
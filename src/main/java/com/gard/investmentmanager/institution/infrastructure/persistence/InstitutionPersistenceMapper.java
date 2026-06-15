package com.gard.investmentmanager.institution.infrastructure.persistence;

import com.gard.investmentmanager.institution.domain.Institution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface InstitutionPersistenceMapper {

    @Mapping(target = "userId", source = "user.id")
    Institution toDomain(InstitutionEntity entity);

    List<Institution> toDomainList(List<InstitutionEntity> entities);
}
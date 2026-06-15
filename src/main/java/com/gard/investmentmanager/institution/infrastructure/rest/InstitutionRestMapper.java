package com.gard.investmentmanager.institution.infrastructure.rest;

import com.gard.investmentmanager.institution.domain.Institution;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface InstitutionRestMapper {

    InstitutionResponse toResponse(Institution institution);

    List<InstitutionResponse> toResponseList(List<Institution> institutions);
}
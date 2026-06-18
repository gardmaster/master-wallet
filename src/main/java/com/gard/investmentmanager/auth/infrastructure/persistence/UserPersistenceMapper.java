package com.gard.investmentmanager.auth.infrastructure.persistence;

import com.gard.investmentmanager.auth.domain.User;
import com.gard.investmentmanager.shared.domain.CurrentUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserPersistenceMapper {

    User toDomain(UserEntity entity);

    @Mapping(target = "externalSubject", source = "externalSubject")
    CurrentUser toCurrentUser(UserEntity entity);
}
package com.gard.investmentmanager.auth.infrastructure.rest;

import com.gard.investmentmanager.auth.domain.User;
import com.gard.investmentmanager.shared.domain.CurrentUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface AuthRestMapper {

    UserResponse toResponse(User user);

    CurrentUserResponse toCurrentUserResponse(CurrentUser currentUser);
}
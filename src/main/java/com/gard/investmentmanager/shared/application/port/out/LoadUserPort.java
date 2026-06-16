package com.gard.investmentmanager.shared.application.port.out;

import com.gard.investmentmanager.shared.domain.CurrentUser;

import java.util.Optional;

public interface LoadCurrentUserPort {

    Optional<CurrentUser> findCurrentUserById(Long userId);

    Optional<CurrentUser> findCurrentUserByExternalSubject(String externalSubject);
}
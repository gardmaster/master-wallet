package com.gard.investmentmanager.auth.application.port.out;

import com.gard.investmentmanager.auth.domain.User;

public interface UserPersistencePort {

    boolean existsByEmail(String email);

    User save(User user);
}
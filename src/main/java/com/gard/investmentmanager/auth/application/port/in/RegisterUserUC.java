package com.gard.investmentmanager.auth.application.port.in;

import com.gard.investmentmanager.auth.domain.User;

public interface RegisterUserUC {

    User execute(RegisterUserCommand command);
}
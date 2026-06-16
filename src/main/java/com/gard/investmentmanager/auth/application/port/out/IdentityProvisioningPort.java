package com.gard.investmentmanager.auth.application.port.out;

import com.gard.investmentmanager.auth.application.port.in.RegisterUserCommand;

public interface IdentityProvisioningPort {

    String createUser(RegisterUserCommand command);

    void deleteUserByExternalSubject(String externalSubject);
}
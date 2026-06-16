package com.gard.investmentmanager.auth.application.service;

import com.gard.investmentmanager.auth.application.port.in.RegisterUserCommand;
import com.gard.investmentmanager.auth.application.port.in.RegisterUserUC;
import com.gard.investmentmanager.auth.application.port.out.IdentityProvisioningPort;
import com.gard.investmentmanager.auth.application.port.out.UserPersistencePort;
import com.gard.investmentmanager.auth.domain.User;
import com.gard.investmentmanager.shared.domain.BusinessException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class RegisterUserService implements RegisterUserUC {

    private final UserPersistencePort userPersistencePort;
    private final IdentityProvisioningPort identityProvisioningPort;
    private final MessageResolver messageResolver;

    public RegisterUserService(
            UserPersistencePort userPersistencePort,
            IdentityProvisioningPort identityProvisioningPort,
            MessageResolver messageResolver
    ) {
        this.userPersistencePort = userPersistencePort;
        this.identityProvisioningPort = identityProvisioningPort;
        this.messageResolver = messageResolver;
    }

    @Override
    @Transactional
    public User execute(RegisterUserCommand command) {
        if (userPersistencePort.existsByEmail(command.email())) {
            throw new BusinessException(
                    messageResolver.get("error.user.email-already-exists", command.email())
            );
        }

        String externalSubject = identityProvisioningPort.createUser(command);

        try {
            Instant now = Instant.now();

            User user = new User(
                    null,
                    command.name().trim(),
                    command.email().trim().toLowerCase(),
                    externalSubject,
                    now,
                    now
            );

            return userPersistencePort.save(user);
        } catch (RuntimeException exception) {
            identityProvisioningPort.deleteUserByExternalSubject(externalSubject);
            throw exception;
        }
    }
}
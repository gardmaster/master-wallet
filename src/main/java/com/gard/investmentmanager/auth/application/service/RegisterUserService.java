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
        String normalizedEmail = command.email().trim().toLowerCase();
        String normalizedFirstName = command.firstName().trim();
        String normalizedLastName = command.lastName().trim();
        String fullName = normalizedFirstName + " " + normalizedLastName;

        if (userPersistencePort.existsByEmail(normalizedEmail)) {
            throw new BusinessException(
                    messageResolver.get("error.user.email-already-exists", normalizedEmail)
            );
        }

        RegisterUserCommand normalizedCommand = new RegisterUserCommand(
                normalizedFirstName,
                normalizedLastName,
                normalizedEmail,
                command.password()
        );

        String externalSubject = identityProvisioningPort.createUser(normalizedCommand);

        try {
            Instant now = Instant.now();

            User user = new User(
                    null,
                    fullName,
                    normalizedEmail,
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
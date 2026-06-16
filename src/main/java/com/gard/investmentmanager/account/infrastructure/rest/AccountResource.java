package com.gard.investmentmanager.account.infrastructure.rest;

import com.gard.investmentmanager.account.application.port.in.CreateAccountCommand;
import com.gard.investmentmanager.account.application.port.in.CreateAccountUC;
import com.gard.investmentmanager.account.application.port.in.DeleteAccountUC;
import com.gard.investmentmanager.account.application.port.in.GetAccountByIdUC;
import com.gard.investmentmanager.account.application.port.in.ListAccountsUC;
import com.gard.investmentmanager.account.application.port.in.UpdateAccountCommand;
import com.gard.investmentmanager.account.application.port.in.UpdateAccountUC;
import com.gard.investmentmanager.account.domain.InvestmentAccount;
import com.gard.investmentmanager.shared.application.port.in.CurrentUserProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class AccountResource implements AccountResourceContract {

    private final CreateAccountUC createAccountUC;
    private final ListAccountsUC listAccountsUC;
    private final GetAccountByIdUC getAccountByIdUC;
    private final UpdateAccountUC updateAccountUC;
    private final DeleteAccountUC deleteAccountUC;
    private final AccountRestMapper accountRestMapper;
    private final CurrentUserProvider currentUserProvider;

    public AccountResource(
            CreateAccountUC createAccountUC,
            ListAccountsUC listAccountsUC,
            GetAccountByIdUC getAccountByIdUC,
            UpdateAccountUC updateAccountUC,
            DeleteAccountUC deleteAccountUC,
            AccountRestMapper accountRestMapper,
            CurrentUserProvider currentUserProvider
    ) {
        this.createAccountUC = createAccountUC;
        this.listAccountsUC = listAccountsUC;
        this.getAccountByIdUC = getAccountByIdUC;
        this.updateAccountUC = updateAccountUC;
        this.deleteAccountUC = deleteAccountUC;
        this.accountRestMapper = accountRestMapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public Response create(CreateAccountRequest request) {
        Long currentUserId = currentUserProvider.getCurrentUser().id();

        InvestmentAccount created = createAccountUC.execute(
                currentUserId,
                new CreateAccountCommand(
                        request.institutionId(),
                        request.name(),
                        request.accountType(),
                        request.baseCurrency(),
                        request.notes()
                )
        );

        AccountResponse response = accountRestMapper.toResponse(created);

        return Response.created(URI.create("/api/v1/accounts/" + response.id()))
                .entity(response)
                .build();
    }

    @Override
    public List<AccountResponse> listAll() {
        Long currentUserId = currentUserProvider.getCurrentUser().id();
        return accountRestMapper.toResponseList(listAccountsUC.execute(currentUserId));
    }

    @Override
    public AccountResponse getById(Long accountId) {
        Long currentUserId = currentUserProvider.getCurrentUser().id();
        return accountRestMapper.toResponse(getAccountByIdUC.execute(currentUserId, accountId));
    }

    @Override
    public AccountResponse update(Long accountId, UpdateAccountRequest request) {
        Long currentUserId = currentUserProvider.getCurrentUser().id();

        return accountRestMapper.toResponse(
                updateAccountUC.execute(
                        currentUserId,
                        accountId,
                        new UpdateAccountCommand(
                                request.institutionId(),
                                request.name(),
                                request.accountType(),
                                request.baseCurrency(),
                                request.notes()
                        )
                )
        );
    }

    @Override
    public Response delete(Long accountId) {
        Long currentUserId = currentUserProvider.getCurrentUser().id();
        deleteAccountUC.execute(currentUserId, accountId);
        return Response.noContent().build();
    }
}
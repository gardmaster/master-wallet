package com.gard.investmentmanager.account.infrastructure.rest;

import com.gard.investmentmanager.account.application.port.in.CreateAccountCommand;
import com.gard.investmentmanager.account.application.port.in.CreateAccountUC;
import com.gard.investmentmanager.account.application.port.in.DeleteAccountUC;
import com.gard.investmentmanager.account.application.port.in.GetAccountByIdUC;
import com.gard.investmentmanager.account.application.port.in.ListAccountsUC;
import com.gard.investmentmanager.account.application.port.in.UpdateAccountCommand;
import com.gard.investmentmanager.account.application.port.in.UpdateAccountUC;
import com.gard.investmentmanager.account.domain.InvestmentAccount;
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

    public AccountResource(
            CreateAccountUC createAccountUC,
            ListAccountsUC listAccountsUC,
            GetAccountByIdUC getAccountByIdUC,
            UpdateAccountUC updateAccountUC,
            DeleteAccountUC deleteAccountUC,
            AccountRestMapper accountRestMapper
    ) {
        this.createAccountUC = createAccountUC;
        this.listAccountsUC = listAccountsUC;
        this.getAccountByIdUC = getAccountByIdUC;
        this.updateAccountUC = updateAccountUC;
        this.deleteAccountUC = deleteAccountUC;
        this.accountRestMapper = accountRestMapper;
    }

    @Override
    public Response create(CreateAccountRequest request) {
        InvestmentAccount created = createAccountUC.execute(
                new CreateAccountCommand(
                        request.userId(),
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
        return accountRestMapper.toResponseList(listAccountsUC.execute());
    }

    @Override
    public AccountResponse getById(Long accountId) {
        return accountRestMapper.toResponse(getAccountByIdUC.execute(accountId));
    }

    @Override
    public AccountResponse update(Long accountId, UpdateAccountRequest request) {
        return accountRestMapper.toResponse(
                updateAccountUC.execute(
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
        deleteAccountUC.execute(accountId);
        return Response.noContent().build();
    }
}
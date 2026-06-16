package com.gard.investmentmanager.account.application.port.in;

public interface DeleteAccountUC {

    void execute(Long currentUserId, Long accountId);
}
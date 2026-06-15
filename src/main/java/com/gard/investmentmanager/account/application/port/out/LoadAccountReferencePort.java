package com.gard.investmentmanager.account.application.port.out;

public interface LoadAccountReferencePort {

    boolean institutionBelongsToUser(Long institutionId, Long userId);
}
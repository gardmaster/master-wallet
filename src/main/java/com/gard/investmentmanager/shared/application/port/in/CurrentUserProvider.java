package com.gard.investmentmanager.shared.application.port.in;

import com.gard.investmentmanager.shared.domain.CurrentUser;

public interface CurrentUserProvider {

    CurrentUser getCurrentUser();
}
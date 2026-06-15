package com.gard.investmentmanager.asset.application.port.in;

import com.gard.investmentmanager.asset.domain.Asset;

public interface CreateAssetUC {

    Asset execute(CreateAssetCommand command);
}
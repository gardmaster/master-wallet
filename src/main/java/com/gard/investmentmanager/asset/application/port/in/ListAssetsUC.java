package com.gard.investmentmanager.asset.application.port.in;

import com.gard.investmentmanager.asset.domain.Asset;

import java.util.List;

public interface ListAssetsUC {

    List<Asset> execute();
}
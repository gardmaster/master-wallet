package com.gard.investmentmanager.asset.application.port.in;

public interface DeleteAssetUC {

    void execute(Long currentUserId, Long assetId);
}
package com.gard.investmentmanager.asset.infrastructure.rest;

import com.gard.investmentmanager.asset.domain.Asset;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface AssetRestMapper {

    AssetResponse toResponse(Asset asset);

    List<AssetResponse> toResponseList(List<Asset> assets);
}
package com.gard.investmentmanager.asset.infrastructure.persistence;

import com.gard.investmentmanager.asset.domain.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface AssetPersistenceMapper {

    @Mapping(target = "userId", source = "user.id")
    Asset toDomain(AssetEntity entity);

    List<Asset> toDomainList(List<AssetEntity> entities);
}
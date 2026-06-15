package com.gard.investmentmanager.asset.infrastructure.persistence;

import com.gard.investmentmanager.asset.application.port.out.AssetPersistencePort;
import com.gard.investmentmanager.asset.domain.Asset;
import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AssetPersistenceAdapter implements AssetPersistencePort {

    private final AssetPanacheRepository assetPanacheRepository;
    private final AssetPersistenceMapper assetPersistenceMapper;
    private final EntityManager entityManager;

    public AssetPersistenceAdapter(
            AssetPanacheRepository assetPanacheRepository,
            AssetPersistenceMapper assetPersistenceMapper,
            EntityManager entityManager
    ) {
        this.assetPanacheRepository = assetPanacheRepository;
        this.assetPersistenceMapper = assetPersistenceMapper;
        this.entityManager = entityManager;
    }

    @Override
    public List<Asset> findAllOrderedByName() {
        return assetPersistenceMapper.toDomainList(assetPanacheRepository.listAllOrderedByName());
    }

    @Override
    public Optional<Asset> findById(Long assetId) {
        return assetPanacheRepository.findByIdOptional(assetId)
                .map(assetPersistenceMapper::toDomain);
    }

    @Override
    public Asset save(Asset asset) {
        AssetEntity entity;

        if (asset.id() == null) {
            entity = new AssetEntity();
        } else {
            entity = assetPanacheRepository.findByIdOptional(asset.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Asset not found: " + asset.id()));
        }

        entity.user = entityManager.getReference(UserEntity.class, asset.userId());
        entity.assetType = asset.assetType();
        entity.name = asset.name();
        entity.ticker = asset.ticker();
        entity.country = asset.country();
        entity.currency = asset.currency();
        entity.issuer = asset.issuer();
        entity.metadataJson = asset.metadataJson();
        entity.createdAt = asset.createdAt();
        entity.updatedAt = asset.updatedAt();

        if (entity.id == null) {
            assetPanacheRepository.persist(entity);
        }

        entityManager.flush();
        return assetPersistenceMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Long assetId) {
        assetPanacheRepository.deleteById(assetId);
    }
}
package com.gard.investmentmanager.institution.infrastructure.persistence;

import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.institution.domain.Institution;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InstitutionPersistenceAdapter implements InstitutionPersistencePort {

    private final InstitutionPanacheRepository institutionPanacheRepository;
    private final InstitutionPersistenceMapper institutionPersistenceMapper;
    private final EntityManager entityManager;

    public InstitutionPersistenceAdapter(
            InstitutionPanacheRepository institutionPanacheRepository,
            InstitutionPersistenceMapper institutionPersistenceMapper,
            EntityManager entityManager
    ) {
        this.institutionPanacheRepository = institutionPanacheRepository;
        this.institutionPersistenceMapper = institutionPersistenceMapper;
        this.entityManager = entityManager;
    }

    @Override
    public List<Institution> findAllOrderedByName() {
        return institutionPersistenceMapper.toDomainList(
                institutionPanacheRepository.listAllOrderedByName()
        );
    }

    @Override
    public Optional<Institution> findById(Long institutionId) {
        return institutionPanacheRepository.findByIdOptional(institutionId)
                .map(institutionPersistenceMapper::toDomain);
    }

    @Override
    public Institution save(Institution institution) {
        InstitutionEntity entity;

        if (institution.id() == null) {
            entity = new InstitutionEntity();
        } else {
            entity = institutionPanacheRepository.findByIdOptional(institution.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Institution not found: " + institution.id()));
        }

        entity.user = entityManager.getReference(UserEntity.class, institution.userId());
        entity.name = institution.name();
        entity.institutionType = institution.institutionType();
        entity.notes = institution.notes();
        entity.createdAt = institution.createdAt();
        entity.updatedAt = institution.updatedAt();

        if (entity.id == null) {
            institutionPanacheRepository.persist(entity);
        }

        entityManager.flush();
        return institutionPersistenceMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Long institutionId) {
        institutionPanacheRepository.deleteById(institutionId);
    }
}
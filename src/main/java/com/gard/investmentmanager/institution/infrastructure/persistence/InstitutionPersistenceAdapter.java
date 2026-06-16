package com.gard.investmentmanager.institution.infrastructure.persistence;

import com.gard.investmentmanager.auth.infrastructure.persistence.UserEntity;
import com.gard.investmentmanager.institution.application.port.out.InstitutionPersistencePort;
import com.gard.investmentmanager.institution.domain.Institution;
import com.gard.investmentmanager.shared.domain.ResourceNotFoundException;
import com.gard.investmentmanager.shared.infrastructure.i18n.MessageResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InstitutionPersistenceAdapter implements InstitutionPersistencePort {

    private final InstitutionPanacheRepository institutionPanacheRepository;
    private final InstitutionPersistenceMapper institutionPersistenceMapper;
    private final EntityManager entityManager;
    private final MessageResolver messageResolver;

    public InstitutionPersistenceAdapter(
            InstitutionPanacheRepository institutionPanacheRepository,
            InstitutionPersistenceMapper institutionPersistenceMapper,
            EntityManager entityManager,
            MessageResolver messageResolver
    ) {
        this.institutionPanacheRepository = institutionPanacheRepository;
        this.institutionPersistenceMapper = institutionPersistenceMapper;
        this.entityManager = entityManager;
        this.messageResolver = messageResolver;
    }

    @Override
    public List<Institution> findAllOrderedByUserIdAndName(Long userId) {
        return institutionPersistenceMapper.toDomainList(
                institutionPanacheRepository.listAllOrderedByUserIdAndName(userId)
        );
    }

    @Override
    public Optional<Institution> findByIdAndUserId(Long institutionId, Long userId) {
        return institutionPanacheRepository.findActiveByIdAndUserIdOptional(institutionId, userId)
                .map(institutionPersistenceMapper::toDomain);
    }

    @Override
    public Institution save(Institution institution) {
        InstitutionEntity entity;

        if (institution.id() == null) {
            entity = new InstitutionEntity();
        } else {
            entity = institutionPanacheRepository.findActiveByIdAndUserIdOptional(institution.id(), institution.userId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            messageResolver.get("error.institution.not-found", institution.id())
                    ));
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
    public void softDeleteById(Long institutionId, Long userId) {
        institutionPanacheRepository.softDeleteById(institutionId, userId, Instant.now());
    }
}
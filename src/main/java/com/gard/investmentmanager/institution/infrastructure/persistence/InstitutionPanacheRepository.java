package com.gard.investmentmanager.institution.infrastructure.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class InstitutionPanacheRepository implements PanacheRepositoryBase<InstitutionEntity, Long> {

    public List<InstitutionEntity> listAllOrderedByName() {
        return find("order by name asc").list();
    }
}
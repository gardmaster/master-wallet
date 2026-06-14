package com.gard.investmentmanager.institution.infrastructure.persistence;

import com.gard.investmentmanager.institution.domain.InstitutionType;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "institutions")
public class InstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "user_id", nullable = false)
    public Long userId;

    @Column(nullable = false, length = 150)
    public String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "institution_type", nullable = false, length = 30)
    public InstitutionType institutionType;

    @Column(columnDefinition = "TEXT")
    public String notes;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;
}
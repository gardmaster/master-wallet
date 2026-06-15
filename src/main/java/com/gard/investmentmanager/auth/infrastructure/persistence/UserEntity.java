package com.gard.investmentmanager.auth.infrastructure.persistence;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 150)
    public String name;

    @Column(nullable = false, length = 255, unique = true)
    public String email;

    @Column(name = "password_hash_or_external_auth_id", nullable = false, length = 255)
    public String passwordHashOrExternalAuthId;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;
}
package com.guiban.fluxOn.domain.userSpecs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserSpecsRepository extends JpaRepository<UserSpecs, UUID> {
    UserSpecs findByUserId(UUID id);
    Optional<UserSpecs> findById(UUID id);
}

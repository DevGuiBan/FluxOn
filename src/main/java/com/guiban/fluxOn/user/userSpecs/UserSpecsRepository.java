package com.guiban.fluxOn.user.userSpecs;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserSpecsRepository extends JpaRepository<UserSpecs, UUID> {
    Optional<UserSpecs> findById(UUID id);
}

package com.guiban.fluxOn.responsibility;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResponsibilityRepository extends JpaRepository<Responsibility, UUID> {
    Responsibility findByName(String name);
}

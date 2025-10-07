package com.guiban.fluxOn.timeClock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TimeClockRepository extends JpaRepository<TimeClock, UUID> {
    List<TimeClock> findByUserId(UUID userId);
}

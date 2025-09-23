package com.guiban.fluxOn.timeClock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TimeClockRepository extends JpaRepository<TimeClock, UUID> {
    TimeClock findByUserId(UUID userId);
}

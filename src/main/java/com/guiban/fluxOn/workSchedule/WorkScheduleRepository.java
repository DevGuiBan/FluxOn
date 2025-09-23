package com.guiban.fluxOn.workSchedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, UUID> {
    WorkSchedule findByUserId(UUID userId);
}

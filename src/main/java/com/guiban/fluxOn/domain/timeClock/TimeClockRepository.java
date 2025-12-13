package com.guiban.fluxOn.domain.timeClock;

import com.guiban.fluxOn.domain.workSchedule.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimeClockRepository extends JpaRepository<TimeClock, UUID> {
    List<TimeClock> findByUserId(UUID userId);
    TimeClock findByUserIdAndDateAndTurn(UUID userId, LocalDate date, Turn turn);
    Optional<TimeClock> findTopByUserIdAndDateAndClockOutIsNullOrderByClockDesc(UUID userId, LocalDate date);
    TimeClock findByUserIdAndDate(UUID userId, LocalDate date);


}

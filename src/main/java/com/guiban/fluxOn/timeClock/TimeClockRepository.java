package com.guiban.fluxOn.timeClock;

import com.guiban.fluxOn.workSchedule.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TimeClockRepository extends JpaRepository<TimeClock, UUID> {
    List<TimeClock> findByUserId(UUID userId);
    TimeClock findByUserIdAndDateAndTurn(UUID userId, LocalDate date, Turn turn);
    TimeClock findByUserIdAndDate(UUID userId, LocalDate date);


}
